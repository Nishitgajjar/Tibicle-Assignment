package com.example.tibicleassignment.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tibicleassignment.adapters.DeviceListAdapter
import com.example.tibicleassignment.databinding.FgFavoriteDeviceBinding
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.mvvm.viewModels.DeviceViewModel
import com.example.tibicleassignment.ui.activities.DeviceActivity
import com.example.tibicleassignment.ui.activities.DeviceDetailActivity

class FavoriteDevicesFragment : Fragment() {

    private val _binding: FgFavoriteDeviceBinding by lazy { FgFavoriteDeviceBinding.inflate(layoutInflater) }
    private val _mobileViewModel: DeviceViewModel by activityViewModels()
    private val _deviceListAdapter: DeviceListAdapter by lazy {
        DeviceListAdapter(mutableListOf()) { _, _, device ->
            goToDetailScreen(device)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = _binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoriteDevices()
        setupSwipeInRecycler()
        filterDeviceList()
    }

    private fun getFavoriteDevices() {
        _binding.rcvFavoriteDevice.adapter = _deviceListAdapter
        _mobileViewModel.addFavoriteLiveData.observe(viewLifecycleOwner) {
            _deviceListAdapter.addList(it)
        }
    }

    private fun filterDeviceList() {
        _mobileViewModel.filterDeviceList.observe(viewLifecycleOwner) {
            _deviceListAdapter.sortingDeviceList(it)
        }
    }

    private fun setupSwipeInRecycler() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedCourse: Device =
                    _deviceListAdapter.listOfMobile[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                _deviceListAdapter.deviceRemoved(position)
                _mobileViewModel.removeFavoriteLiveData.postValue(deletedCourse)
            }
        }).attachToRecyclerView(_binding.rcvFavoriteDevice)
    }

    private fun goToDetailScreen(device: Device) {
        val mainActivity = activity as DeviceActivity
        val detailActivity = Intent(mainActivity, DeviceDetailActivity::class.java).apply { this.putExtra("device", device) }
        startActivity(detailActivity)
    }
}