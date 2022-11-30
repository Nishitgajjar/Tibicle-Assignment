package com.example.tibicleassignment.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tibicleassignment.R
import com.example.tibicleassignment.adapters.DeviceListAdapter
import com.example.tibicleassignment.databinding.FgAllDeviceBinding
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.mvvm.viewModels.DeviceViewModel
import com.example.tibicleassignment.retrofit.ApiResult
import com.example.tibicleassignment.ui.activities.DeviceActivity
import com.example.tibicleassignment.ui.activities.DeviceDetailActivity
import com.example.tibicleassignment.utils.convertToList
import com.example.tibicleassignment.utils.showToast

class AllDeviceFragment : Fragment() {

    private val _binding: FgAllDeviceBinding by lazy { FgAllDeviceBinding.inflate(layoutInflater) }
    private val _mobileViewModel: DeviceViewModel by activityViewModels()

    private val _deviceListAdapter: DeviceListAdapter by lazy {
        DeviceListAdapter(mutableListOf()) { view, position, device ->
            when(view.id) {
                R.id.iv_favorite -> {
                    _deviceListAdapter.markAsFavorite(position)
                    _mobileViewModel.addFavoriteLiveData.postValue(_deviceListAdapter.listOfMobile.filter { it.isFavorite })
                }
                else -> goToDetailScreen(device)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllDevices()
        changeFavoriteDevice()
        filterDeviceList()
    }

    private fun getAllDevices() {
        _binding.rcvAllDevice.adapter = _deviceListAdapter
        _mobileViewModel.getListOfMobiles().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    when (it.data) {
                        is List<*> -> {
                            val response = convertToList<Device>(it.data)
                            _deviceListAdapter.addList(response)
                            _mobileViewModel.addDevices(response)
                            _mobileViewModel.addFavoriteLiveData.postValue(response.filter { it.isFavorite })
                            showToast("${response.size} devices found !")
                        }
                        else -> showToast(resources.getString(R.string.error_server))
                    }
                }
                is ApiResult.Failure -> showToast(resources.getString(R.string.error_server))
                is ApiResult.NoData -> showToast(resources.getString(R.string.no_data))
                is ApiResult.InProgress -> showToast(resources.getString(R.string.loading))
            }
        }
    }

    private fun filterDeviceList() {
        _mobileViewModel.filterDeviceList.observe(viewLifecycleOwner) {
            _deviceListAdapter.sortingDeviceList(it)
        }
    }

    private fun changeFavoriteDevice() {
        _mobileViewModel.removeFavoriteLiveData.observe(viewLifecycleOwner) {
            _deviceListAdapter.removeMarkAsFavorite(it)
        }
    }

    private fun goToDetailScreen(device: Device) {
        val mainActivity = activity as DeviceActivity
        val detailActivity = Intent(mainActivity, DeviceDetailActivity::class.java).apply { this.putExtra("device", device) }
        startActivity(detailActivity)
    }
}