package com.herbalscan.app.ui.herbal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbalscan.app.databinding.ActivityHerbalListBinding
import com.herbalscan.app.data.repository.HerbalRepository
import com.herbalscan.app.utils.Constants

/**
 * Activity 7: Herbal List Activity
 * 
 * Penerapan Materi:
 * - RecyclerView: Menampilkan daftar tanaman herbal
 * - Intent: Navigasi ke HerbalDetailActivity
 * - Bundle: Mengirim data tanaman (Parcelable)
 */
class HerbalListActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHerbalListBinding
    private lateinit var herbalRepository: HerbalRepository
    private lateinit var adapter: HerbalAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        herbalRepository = HerbalRepository()
        
        setupToolbar()
        setupRecyclerView()
        setupSearch()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        val herbalList = herbalRepository.getAllHerbalPlants()
        
        adapter = HerbalAdapter(herbalList) { herbal ->
            // Intent ke HerbalDetailActivity dengan Bundle
            val intent = Intent(this, HerbalDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_PLANT_DATA, herbal)
            startActivity(intent)
        }
        
        binding.rvHerbalList.layoutManager = LinearLayoutManager(this)
        binding.rvHerbalList.adapter = adapter
    }
    
    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                val filteredList = if (query.isEmpty()) {
                    herbalRepository.getAllHerbalPlants()
                } else {
                    herbalRepository.searchHerbalPlants(query)
                }
                adapter.updateList(filteredList)
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
