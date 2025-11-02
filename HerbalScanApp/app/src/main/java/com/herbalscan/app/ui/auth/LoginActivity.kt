package com.herbalscan.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityLoginBinding
import com.herbalscan.app.ui.main.MainActivity
import com.herbalscan.app.utils.SecurityUtils
import com.herbalscan.app.utils.SessionManager

/**
 * Activity 2: Login Activity
 * 
 * Penerapan Materi:
 * - Intent: Navigasi ke RegisterActivity dan MainActivity
 * - SharedPreferences: Menyimpan session login melalui SessionManager
 * - Proteksi: Validasi email dan password, password hashing
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    
    // Simulasi database user (dalam aplikasi nyata, gunakan Room Database)
    private val registeredUsers = mutableMapOf<String, Pair<String, String>>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        
        // Load registered users dari SharedPreferences
        loadRegisteredUsers()
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }
        
        binding.tvRegister.setOnClickListener {
            // Intent ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        
        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Fitur lupa password akan segera hadir", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        
        // Validasi input
        if (!validateInput(email, password)) {
            return
        }
        
        // Hash password untuk pencocokan
        val hashedPassword = SecurityUtils.hashPassword(password)
        
        // Cek kredensial
        val userData = registeredUsers[email]
        if (userData != null && userData.second == hashedPassword) {
            // Login berhasil
            sessionManager.createLoginSession(
                userId = email,
                name = userData.first,
                email = email,
                phone = ""
            )
            
            Toast.makeText(this, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
            
            // Intent ke MainActivity dengan Bundle
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            // Login gagal
            Toast.makeText(this, getString(R.string.error_login_failed), Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        // Validasi email kosong
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_field)
            return false
        }
        
        // Validasi format email
        if (!SecurityUtils.validateEmail(email)) {
            binding.tilEmail.error = getString(R.string.error_invalid_email)
            return false
        }
        
        binding.tilEmail.error = null
        
        // Validasi password kosong
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_field)
            return false
        }
        
        // Validasi panjang password
        if (!SecurityUtils.validatePassword(password)) {
            binding.tilPassword.error = getString(R.string.error_password_short)
            return false
        }
        
        binding.tilPassword.error = null
        
        return true
    }
    
    private fun loadRegisteredUsers() {
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val allEntries = prefs.all
        
        for ((key, value) in allEntries) {
            if (key.endsWith("_password")) {
                val email = key.removeSuffix("_password")
                val name = prefs.getString("${email}_name", "") ?: ""
                val password = value as String
                registeredUsers[email] = Pair(name, password)
            }
        }
        
        // Tambahkan user default untuk testing
        if (registeredUsers.isEmpty()) {
            val defaultEmail = "test@herbalscan.com"
            val defaultPassword = SecurityUtils.hashPassword("123456")
            registeredUsers[defaultEmail] = Pair("Test User", defaultPassword)
        }
    }
}
