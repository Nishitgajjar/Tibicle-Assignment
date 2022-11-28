package com.example.tibicleassignment.retrofit

import com.example.tibicleassignment.BuildConfig.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient() {

    private val okHttpBuilder: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().serializeSpecialFloatingPointValues().setLenient().create()))
                .client(okHttpBuilder)
                .build()
    }

    val retroInstance: Api by lazy { retrofit.create(Api::class.java) }
}