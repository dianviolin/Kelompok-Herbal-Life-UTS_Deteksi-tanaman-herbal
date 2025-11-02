package com.herbalscan.app.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityRegisterBinding
import com.herbalscan.app.utils.SecurityUtils

/**
 * Activity 3: Register Activity
 * 
 * Penerapan Materi:
 * - Intent: Navigasi kembali ke LoginActivity
 * - SharedPreferences: Menyimpan data user yang terdaftar
 * - Proteksi: Validasi semua input, password matching, password hashing
 */
class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            performRegister()
        }
        
        binding.tvLogin.setOnClickListener {
            // Kembali ke LoginActivity
            finish()
        }
    }
    
    private fun performRegister() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        
        // Validasi input
        if (!validateInput(fullName, email, phone, password, confirmPassword)) {
            return
        }
        
        // Cek apakah email sudah terdaftar
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        if (prefs.contains("${email}_password")) {
            binding.tilEmail.error = "Email sudah terdaftar"
            return
        }
        
        // Hash password sebelum disimpan
        val hashedPassword = SecurityUtils.hashPassword(password)
        
        // Simpan data user ke SharedPreferences
        prefs.edit().apply {
            putString("${email}_name", fullName)
            putString("${email}_phone", phone)
            putString("${email}_password", hashedPassword)
            apply()
        }
        
        Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
        
        // Kembali ke LoginActivity
        finish()
    }
    
    private fun validateInput(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true
        
        // Validasi nama lengkap
        if (fullName.isEmpty()) {
            binding.tilFullName.error = getString(R.string.error_empty_field)
            isValid = false
        } else {
            binding.tilFullName.error = null
        }
        
        // Validasi email
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (!SecurityUtils.validateEmail(email)) {
            binding.tilEmail.error = getString(R.string.error_invalid_email)
            isValid = false
        } else {
            binding.tilEmail.error = null
        }
        
        // Validasi nomor telepon
        if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (!SecurityUtils.validatePhone(phone)) {
            binding.tilPhone.error = "Nomor telepon tidak valid"
            isValid = false
        } else {
            binding.tilPhone.error = null
        }
        
        // Validasi password
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (!SecurityUtils.validatePassword(password)) {
            binding.tilPassword.error = getString(R.string.error_password_short)
            isValid = false
        } else {
            binding.tilPassword.error = null
        }
        
        // Validasi konfirmasi password
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = getString(R.string.error_empty_field)
            isValid = false
        } else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = getString(R.string.error_password_mismatch)
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }
        
        return isValid
    }
}
