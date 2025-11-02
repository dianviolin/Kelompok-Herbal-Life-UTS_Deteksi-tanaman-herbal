package com.herbalscan.app.utils

import java.security.MessageDigest

object SecurityUtils {
    
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
    
    fun validateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    
    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun validatePhone(phone: String): Boolean {
        return phone.length >= 10 && phone.all { it.isDigit() }
    }
}
