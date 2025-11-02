package com.herbalscan.app.ui.herbal

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityHerbalDetailBinding
import com.herbalscan.app.data.model.HerbalPlant
import com.herbalscan.app.utils.Constants

/**
 * Activity 8: Herbal Detail Activity
 * 
 * Penerapan Materi:
 * - Bundle: Menerima data HerbalPlant (Parcelable) dari Intent
 * - Intent: Menerima data dari HerbalListActivity atau ResultActivity
 */
class HerbalDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHerbalDetailBinding
    private var herbalPlant: HerbalPlant? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerbalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        getDataFromIntent()
        displayHerbalDetail()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun getDataFromIntent() {
        // Bundle: Menerima data Parcelable dari Intent
        herbalPlant = intent.getParcelableExtra(Constants.EXTRA_PLANT_DATA)
    }
    
    private fun displayHerbalDetail() {
        herbalPlant?.let { plant ->
            // Set toolbar title
            binding.collapsingToolbar.title = plant.name
            
            // Display data
            binding.tvHerbalName.text = plant.name
            binding.tvLatinName.text = plant.latinName
            binding.tvDescription.text = plant.description
            binding.tvUsage.text = plant.usage
            
            // Display benefits list
            binding.llBenefits.removeAllViews()
            plant.benefits.forEachIndexed { index, benefit ->
                val benefitView = LayoutInflater.from(this)
                    .inflate(android.R.layout.simple_list_item_1, binding.llBenefits, false)
                
                val textView = benefitView.findViewById<TextView>(android.R.id.text1)
                textView.text = "${index + 1}. $benefit"
                textView.setTextColor(getColor(R.color.text_secondary))
                textView.textSize = 14f
                textView.setPadding(0, 8, 0, 8)
                
                binding.llBenefits.addView(benefitView)
            }
        }
    }
}
