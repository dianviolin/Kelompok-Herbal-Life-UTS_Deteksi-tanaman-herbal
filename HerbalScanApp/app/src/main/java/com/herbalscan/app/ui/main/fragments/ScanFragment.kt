package com.herbalscan.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herbalscan.app.databinding.FragmentScanBinding
import com.herbalscan.app.ui.scan.ScanActivity

/**
 * Fragment 2: Scan Fragment
 * 
 * Penerapan Materi:
 * - Fragment: Bagian dari MainActivity
 * - Intent: Navigasi ke ScanActivity
 */
class ScanFragment : Fragment() {
    
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnOpenScan.setOnClickListener {
            // Intent ke ScanActivity
            val intent = Intent(requireContext(), ScanActivity::class.java)
            startActivity(intent)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
