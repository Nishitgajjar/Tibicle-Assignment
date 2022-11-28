package com.example.tibicleassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeviceImage(
    @SerializedName("url")
    @Expose
    val mobileImgUrl: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("mobile_id")
    @Expose
    val mobileId: Int
)
