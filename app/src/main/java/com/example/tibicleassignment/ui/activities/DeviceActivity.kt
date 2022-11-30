package com.example.tibicleassignment.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tibicleassignment.R
import com.example.tibicleassignment.R.id
import com.example.tibicleassignment.databinding.ActivityDevicesBinding
import com.example.tibicleassignment.ui.fragment.AllDeviceFragment
import com.example.tibicleassignment.ui.fragment.FavoriteDevicesFragment
import com.example.tibicleassignment.utils.CustomPagerAdapter
import com.example.tibicleassignment.mvvm.viewModels.DeviceViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DeviceActivity : AppCompatActivity() {

    private val _binding: ActivityDevicesBinding by lazy { ActivityDevicesBinding.inflate(layoutInflater) }
    private val _mobileViewModel: DeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        bindingViewPager()
    }

    private fun bindingViewPager() {
        val customPagerAdapter = CustomPagerAdapter(this)
        customPagerAdapter.addFragment(AllDeviceFragment())
        customPagerAdapter.addFragment(FavoriteDevicesFragment())
        _binding.deviceViewPager.adapter = customPagerAdapter
        TabLayoutMediator(_binding.deviceTabLayout, _binding.deviceViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "All devices"
                1 -> tab.text = "Favorites"
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.device_sorting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.menu_price_low_to_high -> _mobileViewModel.filterDeviceList.postValue(1)
            id.menu_price_high_to_low -> _mobileViewModel.filterDeviceList.postValue(2)
            id.menu_rating_5_to_1 -> _mobileViewModel.filterDeviceList.postValue(3)
        }
        return super.onOptionsItemSelected(item)
    }
}