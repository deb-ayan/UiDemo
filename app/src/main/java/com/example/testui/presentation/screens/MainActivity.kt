package com.example.testui.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Applications"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Settings"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

    }
}