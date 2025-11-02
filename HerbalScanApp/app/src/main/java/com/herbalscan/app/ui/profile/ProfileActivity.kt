package com.herbalscan.app.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.herbalscan.app.databinding.ActivityProfileBinding
import com.herbalscan.app.data.database.AppDatabase
import com.herbalscan.app.data.repository.HerbalRepository
import com.herbalscan.app.utils.SessionManager
import kotlinx.coroutines.launch

/**
 * Activity 9: Profile Activity
 * 
 * Penerapan Materi:
 * - SharedPreferences: Mengambil data user dari SessionManager
 * - Threading: Mengambil data statistik dari database dengan Coroutine
 */
class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var database: AppDatabase
    private lateinit var herbalRepository: HerbalRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        database = AppDatabase.getDatabase(this)
        herbalRepository = HerbalRepository()
        
        setupToolbar()
        loadUserData()
        loadStatistics()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadUserData() {
        val userName = sessionManager.getUserName()
        val userEmail = sessionManager.getUserEmail()
        val userPhone = sessionManager.getUserPhone()
        
        binding.tvUserName.text = userName
        binding.tvFullName.text = userName
        binding.tvEmail.text = userEmail
        binding.tvPhone.text = if (userPhone.isNotEmpty()) userPhone else "-"
    }
    
    private fun loadStatistics() {
        // Threading: Menggunakan Coroutine untuk mengambil data dari database
        lifecycleScope.launch {
            try {
                // Ambil jumlah total scan dari database
                database.detectionDao().getAllDetections().collect { detections ->
                    binding.tvScanCount.text = detections.size.toString()
                }
            } catch (e: Exception) {
                binding.tvScanCount.text = "0"
            }
        }
        
        // Jumlah jenis tanaman dari repository
        val plantCount = herbalRepository.getAllHerbalPlants().size
        binding.tvPlantCount.text = plantCount.toString()
    }
}
