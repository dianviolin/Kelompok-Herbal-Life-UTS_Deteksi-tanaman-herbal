package com.herbalscan.app.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityHistoryBinding
import com.herbalscan.app.data.database.AppDatabase
import com.herbalscan.app.data.model.DetectionResult
import kotlinx.coroutines.launch

/**
 * Activity 10 (BONUS): History Activity
 * 
 * Penerapan Materi:
 * - Room Database: Menyimpan dan mengambil riwayat deteksi
 * - Threading: Operasi database dengan Coroutine
 * - RecyclerView: Menampilkan daftar riwayat
 */
class HistoryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: HistoryAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = AppDatabase.getDatabase(this)
        
        setupToolbar()
        setupRecyclerView()
        loadHistory()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        adapter = HistoryAdapter(emptyList()) { detection ->
            showDeleteDialog(detection)
        }
        
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter
    }
    
    private fun loadHistory() {
        // Threading: Menggunakan Coroutine untuk mengambil data dari database
        lifecycleScope.launch {
            database.detectionDao().getAllDetections().collect { detections ->
                if (detections.isEmpty()) {
                    binding.llEmptyState.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.GONE
                } else {
                    binding.llEmptyState.visibility = View.GONE
                    binding.rvHistory.visibility = View.VISIBLE
                    adapter.updateList(detections)
                }
            }
        }
    }
    
    private fun showDeleteDialog(detection: DetectionResult) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Riwayat")
            .setMessage("Apakah Anda yakin ingin menghapus riwayat ini?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteHistory(detection)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun deleteHistory(detection: DetectionResult) {
        // Threading: Menggunakan Coroutine untuk menghapus data dari database
        lifecycleScope.launch {
            try {
                database.detectionDao().deleteDetection(detection)
                Toast.makeText(
                    this@HistoryActivity,
                    "Riwayat berhasil dihapus",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this@HistoryActivity,
                    "Gagal menghapus: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
