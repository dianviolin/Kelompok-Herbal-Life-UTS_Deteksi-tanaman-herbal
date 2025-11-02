package com.herbalscan.app.ui.result

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.herbalscan.app.databinding.ActivityResultBinding
import com.herbalscan.app.data.database.AppDatabase
import com.herbalscan.app.data.model.DetectionResult
import com.herbalscan.app.data.repository.HerbalRepository
import com.herbalscan.app.ui.herbal.HerbalDetailActivity
import com.herbalscan.app.ui.scan.ScanActivity
import com.herbalscan.app.utils.Constants
import kotlinx.coroutines.launch

/**
 * Activity 6: Result Activity
 * 
 * Penerapan Materi:
 * - Bundle: Menerima data dari ScanActivity
 * - Intent: Navigasi ke HerbalDetailActivity dengan data
 * - Threading: Menyimpan data ke database dengan Coroutine
 */
class ResultActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityResultBinding
    private lateinit var herbalRepository: HerbalRepository
    private lateinit var database: AppDatabase
    
    private var plantName: String = ""
    private var confidence: Float = 0f
    private var imagePath: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        herbalRepository = HerbalRepository()
        database = AppDatabase.getDatabase(this)
        
        setupToolbar()
        getDataFromIntent()
        displayResult()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun getDataFromIntent() {
        // Bundle: Menerima data dari Intent
        plantName = intent.getStringExtra(Constants.EXTRA_PLANT_NAME) ?: ""
        confidence = intent.getFloatExtra(Constants.EXTRA_CONFIDENCE, 0f)
        imagePath = intent.getStringExtra(Constants.EXTRA_IMAGE_PATH) ?: ""
    }
    
    private fun displayResult() {
        binding.tvPlantName.text = plantName
        
        val confidencePercent = (confidence * 100).toInt()
        binding.tvConfidence.text = "$confidencePercent%"
        binding.progressConfidence.progress = confidencePercent
    }
    
    private fun setupListeners() {
        binding.btnViewDetail.setOnClickListener {
            // Cari data tanaman dari repository
            val plant = herbalRepository.getAllHerbalPlants()
                .find { it.name.equals(plantName, ignoreCase = true) }
            
            if (plant != null) {
                // Intent ke HerbalDetailActivity dengan Bundle (Parcelable)
                val intent = Intent(this, HerbalDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_PLANT_DATA, plant)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data tanaman tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnSaveResult.setOnClickListener {
            saveResultToDatabase()
        }
        
        binding.btnScanAgain.setOnClickListener {
            // Intent ke ScanActivity
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
    private fun saveResultToDatabase() {
        // Threading: Menggunakan Coroutine untuk operasi database
        lifecycleScope.launch {
            try {
                val detection = DetectionResult(
                    plantName = plantName,
                    confidence = confidence,
                    imagePath = imagePath
                )
                
                database.detectionDao().insertDetection(detection)
                
                Toast.makeText(
                    this@ResultActivity,
                    "Hasil deteksi berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()
                
                binding.btnSaveResult.isEnabled = false
                binding.btnSaveResult.text = "Tersimpan"
                
            } catch (e: Exception) {
                Toast.makeText(
                    this@ResultActivity,
                    "Gagal menyimpan: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
