package com.example.tibicleassignment.mvvm.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.models.DeviceImage
import com.example.tibicleassignment.mvvm.DeviceDatabase

class DeviceRepository(application: Application) {

    private val deviceDatabase by lazy { DeviceDatabase.getInstance(application.applicationContext) }

    fun getDevices(): LiveData<List<Device>> { return deviceDatabase.deviceDao().deviceLiveData }
    fun getDeviceImages(deviceId: Int): LiveData<List<DeviceImage>> { return deviceDatabase.deviceDao().deviceImages(deviceId) }

    suspend fun insertDevices(listOfDevices: List<Device>) = deviceDatabase.deviceDao().addDevices(listOfDevices)
    suspend fun insertDeviceImages(listOfDevices: List<DeviceImage>) = deviceDatabase.deviceDao().addDeviceImages(listOfDevices)
}