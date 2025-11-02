package com.herbalscan.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herbalscan.app.data.model.DetectionResult
import com.herbalscan.app.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private var historyList: List<DetectionResult>,
    private val onDeleteClick: (DetectionResult) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    
    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(detection: DetectionResult) {
            binding.tvPlantName.text = detection.plantName
            
            val confidencePercent = (detection.confidence * 100).toInt()
            binding.tvConfidence.text = "Confidence: $confidencePercent%"
            
            val dateFormat = SimpleDateFormat("d MMM yyyy, HH:mm", Locale.getDefault())
            binding.tvTimestamp.text = dateFormat.format(Date(detection.timestamp))
            
            binding.ivDelete.setOnClickListener {
                onDeleteClick(detection)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }
    
    override fun getItemCount(): Int = historyList.size
    
    fun updateList(newList: List<DetectionResult>) {
        historyList = newList
        notifyDataSetChanged()
    }
}
