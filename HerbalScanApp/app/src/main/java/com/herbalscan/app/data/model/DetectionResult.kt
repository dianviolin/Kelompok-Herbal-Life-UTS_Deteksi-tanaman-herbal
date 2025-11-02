package com.herbalscan.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_history")
data class DetectionResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantName: String,
    val confidence: Float,
    val imagePath: String,
    val timestamp: Long = System.currentTimeMillis()
)
