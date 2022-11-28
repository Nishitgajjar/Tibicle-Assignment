package com.example.tibicleassignment.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("mobiles/")
    suspend fun // get list of mobiles
            getListOfMobiles(): Response<Any>

    @GET("mobiles/{mobile_id}/images/")
    suspend fun // get mobile images
            getMobileImages(@Path("mobile_id") mobileId: Int): Response<Any>
}