package com.example.testui.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testui.presentation.screens.fragments.ApplicationsFragment
import com.example.testui.presentation.screens.fragments.SettingsFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ApplicationsFragment()
            else -> SettingsFragment()
        }
    }
}