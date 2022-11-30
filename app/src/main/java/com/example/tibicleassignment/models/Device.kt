package com.example.tibicleassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Device(
    @PrimaryKey
    @SerializedName("price")
    @Expose
    val price: Double,

    @SerializedName("brand")
    @Expose
    val brand: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("thumbImageURL")
    @Expose
    val thumbImageURL: String,

    @SerializedName("rating")
    @Expose
    val rating: Double,

    @SerializedName("name")
    @Expose
    val deviceName: String,

    @SerializedName("description")
    @Expose
    val description: String
) : java.io.Serializable {
    var isFavorite: Boolean = false
}