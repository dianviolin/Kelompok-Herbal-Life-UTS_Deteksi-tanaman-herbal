package com.herbalscan.app.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.databinding.ActivitySettingsBinding

/**
 * Activity 11 (BONUS): Settings Activity
 * 
 * Penerapan Materi:
 * - SharedPreferences: Menyimpan preferensi pengguna (notifikasi, dark mode)
 * - Intent: Navigasi dan interaksi dengan user
 */
class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferences
    
    companion object {
        private const val PREF_NAME = "AppSettings"
        private const val KEY_NOTIFICATIONS = "notifications"
        private const val KEY_DARK_MODE = "dark_mode"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        
        setupToolbar()
        loadSettings()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadSettings() {
        // Load saved preferences
        binding.switchNotifications.isChecked = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        binding.switchDarkMode.isChecked = prefs.getBoolean(KEY_DARK_MODE, false)
    }
    
    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // Save notification preference
            prefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
            
            val message = if (isChecked) {
                "Notifikasi diaktifkan"
            } else {
                "Notifikasi dinonaktifkan"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Save dark mode preference
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
            
            val message = if (isChecked) {
                "Mode gelap diaktifkan (Restart aplikasi untuk melihat perubahan)"
            } else {
                "Mode gelap dinonaktifkan"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        
        binding.llAbout.setOnClickListener {
            showAboutDialog()
        }
    }
    
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Tentang HerbalScan")
            .setMessage(
                "HerbalScan adalah aplikasi mobile untuk mendeteksi dan memberikan informasi " +
                "tentang tanaman herbal Indonesia.\n\n" +
                "Aplikasi ini dibuat sebagai tugas UTS Pemrograman Mobile.\n\n" +
                "Versi: 1.0.0\n" +
                "© 2025 HerbalScan Team"
            )
            .setPositiveButton("OK", null)
            .show()
    }
}
