package com.herbalscan.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android:view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.herbalscan.app.R
import com.herbalscan.app.databinding.FragmentProfileBinding
import com.herbalscan.app.ui.auth.LoginActivity
import com.herbalscan.app.ui.profile.ProfileActivity
import com.herbalscan.app.utils.SessionManager

/**
 * Fragment 3: Profile Fragment
 * 
 * Penerapan Materi:
 * - Fragment: Bagian dari MainActivity
 * - Intent: Navigasi ke ProfileActivity dan LoginActivity
 * - SharedPreferences: Mengambil data user dan logout
 */
class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var sessionManager: SessionManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sessionManager = SessionManager(requireContext())
        
        loadUserData()
        setupClickListeners()
    }
    
    private fun loadUserData() {
        val userName = sessionManager.getUserName()
        val userEmail = sessionManager.getUserEmail()
        val userPhone = sessionManager.getUserPhone()
        
        binding.tvUserName.text = userName
        binding.tvUserEmail.text = userEmail
        binding.tvEmail.text = userEmail
        binding.tvPhone.text = if (userPhone.isNotEmpty()) userPhone else "-"
    }
    
    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            // Intent ke ProfileActivity
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }
    
    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                performLogout()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun performLogout() {
        sessionManager.logout()
        
        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
        
        // Intent ke LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
