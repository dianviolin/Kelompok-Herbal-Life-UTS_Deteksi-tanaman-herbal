package com.herbalscan.app.ui.scan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityScanBinding
import com.herbalscan.app.ui.result.ResultActivity
import com.herbalscan.app.utils.Constants
import kotlin.random.Random

/**
 * Activity 5: Scan Activity
 * 
 * Penerapan Materi:
 * - Intent: Navigasi ke ResultActivity dengan data
 * - Bundle: Mengirim data hasil deteksi
 * - Threading: Simulasi proses deteksi dengan Handler
 */
class ScanActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityScanBinding
    private var hasImage = false
    
    // Data tanaman herbal untuk simulasi deteksi
    private val herbalPlants = listOf(
        "Jahe", "Kunyit", "Temulawak", "Lengkuas", "Kencur"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupListeners() {
        binding.btnTakePhoto.setOnClickListener {
            // Simulasi ambil foto dari kamera
            Toast.makeText(this, "Fitur kamera akan segera hadir", Toast.LENGTH_SHORT).show()
            simulateImageSelected()
        }
        
        binding.btnChooseGallery.setOnClickListener {
            // Simulasi pilih dari galeri
            Toast.makeText(this, "Simulasi: Gambar dipilih dari galeri", Toast.LENGTH_SHORT).show()
            simulateImageSelected()
        }
        
        binding.btnStartScan.setOnClickListener {
            if (hasImage) {
                performScan()
            }
        }
    }
    
    private fun simulateImageSelected() {
        hasImage = true
        binding.tvPlaceholder.visibility = View.GONE
        binding.ivPreview.setImageResource(android.R.drawable.ic_menu_gallery)
        binding.btnStartScan.isEnabled = true
    }
    
    private fun performScan() {
        // Tampilkan loading
        binding.progressBar.visibility = View.VISIBLE
        binding.btnStartScan.isEnabled = false
        binding.btnTakePhoto.isEnabled = false
        binding.btnChooseGallery.isEnabled = false
        
        // Threading: Simulasi proses deteksi dengan delay 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            // Simulasi hasil deteksi random
            val detectedPlant = herbalPlants.random()
            val confidence = Random.nextFloat() * 0.3f + 0.7f // 70-100%
            
            // Intent ke ResultActivity dengan Bundle
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.EXTRA_PLANT_NAME, detectedPlant)
            intent.putExtra(Constants.EXTRA_CONFIDENCE, confidence)
            intent.putExtra(Constants.EXTRA_IMAGE_PATH, "simulated_image")
            
            startActivity(intent)
            finish()
            
        }, 2000)
    }
}
