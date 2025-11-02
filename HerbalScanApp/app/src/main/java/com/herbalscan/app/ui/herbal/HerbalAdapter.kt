package com.herbalscan.app.ui.herbal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herbalscan.app.data.model.HerbalPlant
import com.herbalscan.app.databinding.ItemHerbalBinding

class HerbalAdapter(
    private var herbalList: List<HerbalPlant>,
    private val onItemClick: (HerbalPlant) -> Unit
) : RecyclerView.Adapter<HerbalAdapter.HerbalViewHolder>() {
    
    inner class HerbalViewHolder(private val binding: ItemHerbalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(herbal: HerbalPlant) {
            binding.tvHerbalName.text = herbal.name
            binding.tvLatinName.text = herbal.latinName
            binding.tvCategory.text = herbal.category
            
            binding.root.setOnClickListener {
                onItemClick(herbal)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HerbalViewHolder {
        val binding = ItemHerbalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HerbalViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HerbalViewHolder, position: Int) {
        holder.bind(herbalList[position])
    }
    
    override fun getItemCount(): Int = herbalList.size
    
    fun updateList(newList: List<HerbalPlant>) {
        herbalList = newList
        notifyDataSetChanged()
    }
}
