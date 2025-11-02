package com.herbalscan.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herbalscan.app.databinding.FragmentHomeBinding
import com.herbalscan.app.ui.herbal.HerbalListActivity
import com.herbalscan.app.ui.history.HistoryActivity
import com.herbalscan.app.ui.scan.ScanActivity
import com.herbalscan.app.ui.settings.SettingsActivity
import com.herbalscan.app.utils.SessionManager

/**
 * Fragment 1: Home Fragment
 * 
 * Penerapan Materi:
 * - Fragment: Bagian dari MainActivity
 * - Intent: Navigasi ke berbagai Activity
 * - Bundle: Menerima data dari Activity
 */
class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var sessionManager: SessionManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sessionManager = SessionManager(requireContext())
        
        // Tampilkan nama user
        binding.tvUserName.text = sessionManager.getUserName()
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.cardScan.setOnClickListener {
            // Intent ke ScanActivity
            val intent = Intent(requireContext(), ScanActivity::class.java)
            startActivity(intent)
        }
        
        binding.cardHerbalList.setOnClickListener {
            // Intent ke HerbalListActivity
            val intent = Intent(requireContext(), HerbalListActivity::class.java)
            startActivity(intent)
        }
        
        binding.cardHistory.setOnClickListener {
            // Intent ke HistoryActivity
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
        
        binding.cardSettings.setOnClickListener {
            // Intent ke SettingsActivity
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
