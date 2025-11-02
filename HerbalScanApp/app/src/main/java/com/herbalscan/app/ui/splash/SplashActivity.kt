package com.herbalscan.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivitySplashBinding
import com.herbalscan.app.ui.auth.LoginActivity
import com.herbalscan.app.ui.main.MainActivity
import com.herbalscan.app.utils.Constants
import com.herbalscan.app.utils.SessionManager

/**
 * Activity 1: Splash Screen
 * 
 * Penerapan Materi:
 * - Threading: Menggunakan Handler dan Looper untuk delay 3 detik
 * - Intent: Navigasi ke LoginActivity atau MainActivity
 * - SharedPreferences: Cek status login melalui SessionManager
 */
class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sessionManager: SessionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        
        // Animasi fade in untuk logo dan text
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.ivLogo.startAnimation(fadeIn)
        binding.tvAppName.startAnimation(fadeIn)
        binding.tvTagline.startAnimation(fadeIn)
        
        // Threading: Menggunakan Handler untuk delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, Constants.SPLASH_DELAY)
    }
    
    private fun navigateToNextScreen() {
        val intent = if (sessionManager.isLoggedIn()) {
            // Jika sudah login, langsung ke MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // Jika belum login, ke LoginActivity
            Intent(this, LoginActivity::class.java)
        }
        
        startActivity(intent)
        finish()
        
        // Animasi transisi
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
