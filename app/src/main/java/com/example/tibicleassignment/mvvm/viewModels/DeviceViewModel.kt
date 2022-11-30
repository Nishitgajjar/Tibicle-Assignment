package com.example.tibicleassignment.mvvm.viewModels

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.models.DeviceImage
import com.example.tibicleassignment.mvvm.repositories.DeviceRepository
import com.example.tibicleassignment.retrofit.ApiResult
import com.example.tibicleassignment.retrofit.RetrofitClient
import com.example.tibicleassignment.utils.crateNetworkResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceViewModel(application: Application) : AndroidViewModel(application) {

    private val deviceRepo by lazy { DeviceRepository(application) }
    private val langResource: Resources = application.resources
    val filterDeviceList = MutableLiveData<Int>()
    val addFavoriteLiveData = MutableLiveData<List<Device>>()
    val removeFavoriteLiveData = MutableLiveData<Device>()

    fun getListOfMobiles(): LiveData<ApiResult<*>> {
        val mutableLiveData = MutableLiveData<ApiResult<*>>()
        mutableLiveData.postValue(ApiResult.InProgress)
        try {
            viewModelScope.launch {
                withContext(IO) {
                    RetrofitClient().retroInstance.getListOfMobiles().crateNetworkResponse(langResource, mutableLiveData)
                }
            }
        } catch (e: Exception) {
            Log.e("listOfMobile :", e.message.orEmpty())
            mutableLiveData.postValue(ApiResult.Success(deviceRepo.getDevices()))
        }
        return mutableLiveData
    }

    fun getMobileImages(mobileId: Int): LiveData<ApiResult<*>> {
        val mutableLiveData = MutableLiveData<ApiResult<*>>()
        mutableLiveData.postValue(ApiResult.InProgress)
        try {
            viewModelScope.launch {
                withContext(IO) {
                    RetrofitClient().retroInstance.getMobileImages(mobileId).crateNetworkResponse(langResource, mutableLiveData)
                }
            }
        } catch (e: Exception) {
            Log.e("listOfMobileImg :", e.message.orEmpty())
            mutableLiveData.postValue(ApiResult.Success(deviceRepo.getDeviceImages(mobileId)))
        }
        return mutableLiveData
    }

    fun addDevices(listOfDevices: List<Device>) {
        viewModelScope.launch { withContext(IO) { deviceRepo.insertDevices(listOfDevices) } }
    }

    fun addDeviceImages(listOfDeviceImages: List<DeviceImage>) {
        viewModelScope.launch { withContext(IO) { deviceRepo.insertDeviceImages(listOfDeviceImages) } }
    }
}