package com.example.tibicleassignment.mvvm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.models.DeviceImage

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDevices(listOfDevices: List<Device>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDeviceImages(listOfDevices: List<DeviceImage>)

    @get:Query("SELECT * FROM Device")
    val deviceLiveData: LiveData<List<Device>>

    @Query("SELECT * FROM DeviceImage WHERE mobileId =:deviceId")
    fun deviceImages(deviceId: Int): LiveData<List<DeviceImage>>
}