package com.example.tibicleassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class DeviceImage(
    @SerializedName("url")
    @Expose
    val mobileImgUrl: String,

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("mobile_id")
    @Expose
    val mobileId: Int
)
