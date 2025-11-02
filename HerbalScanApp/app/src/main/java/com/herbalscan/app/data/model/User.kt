package com.herbalscan.app.data.model

data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
