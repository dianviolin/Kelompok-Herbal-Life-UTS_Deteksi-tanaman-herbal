package com.herbalscan.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.herbalscan.app.R
import com.herbalscan.app.databinding.ActivityMainBinding
import com.herbalscan.app.ui.main.fragments.HomeFragment
import com.herbalscan.app.ui.main.fragments.ProfileFragment
import com.herbalscan.app.ui.main.fragments.ScanFragment

/**
 * Activity 4: Main Activity (Home)
 * 
 * Penerapan Materi:
 * - Fragment: Menggunakan 3 Fragment (Home, Scan, Profile) dengan Bottom Navigation
 * - Intent: Navigasi ke berbagai Activity lain
 * - Bundle: Mengirim data antar Fragment
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        
        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            binding.toolbar.title = getString(R.string.home_title)
        }
        
        setupBottomNavigation()
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    binding.toolbar.title = getString(R.string.home_title)
                    true
                }
                R.id.nav_scan -> {
                    loadFragment(ScanFragment())
                    binding.toolbar.title = getString(R.string.scan_title)
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    binding.toolbar.title = getString(R.string.profile_title)
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
