package com.herbalscan.app.data.database

import androidx.room.*
import com.herbalscan.app.data.model.DetectionResult
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionDao {
    
    @Query("SELECT * FROM detection_history ORDER BY timestamp DESC")
    fun getAllDetections(): Flow<List<DetectionResult>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetection(detection: DetectionResult)
    
    @Delete
    suspend fun deleteDetection(detection: DetectionResult)
    
    @Query("DELETE FROM detection_history")
    suspend fun clearAllDetections()
    
    @Query("SELECT * FROM detection_history WHERE id = :id")
    suspend fun getDetectionById(id: Long): DetectionResult?
}
