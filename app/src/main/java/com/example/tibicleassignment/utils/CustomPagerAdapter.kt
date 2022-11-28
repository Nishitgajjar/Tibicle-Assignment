package com.example.tibicleassignment.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = mutableListOf<Fragment>()

    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)
}