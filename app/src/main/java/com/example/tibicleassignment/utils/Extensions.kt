package com.example.tibicleassignment.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tibicleassignment.R
import com.example.tibicleassignment.retrofit.ApiResult
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Response
import java.io.Serializable

// for every api calls
fun Response<Any>.crateNetworkResponse(langResource: Resources, resResults: MutableLiveData<ApiResult<*>>): Any {
    try {
        when (this.isSuccessful && this.body() != null) {
            true -> when (this.code()) {
                Constants.SUCCESS_CODE -> return resResults.postValue(ApiResult.Success(this.body()!!))
                Constants.FAILURE_CODE -> return resResults.postValue(ApiResult.NoData(""))
                Constants.SOCKET_CODE -> return resResults.postValue(ApiResult.NoData(langResource.getString(R.string.error_network)))
                Constants.SERVER_CODE -> return resResults.postValue(ApiResult.NoData(langResource.getString(R.string.error_server)))
            }
            else -> return resResults.postValue(ApiResult.NoData(langResource.getString(R.string.error_unknown)))
        }
        return resResults.postValue(ApiResult.NoData(langResource.getString(R.string.error_unknown)))
    }
    catch (e: Exception) {
        return resResults.postValue(ApiResult.Failure(e))
    }
}

// activity toast
fun Context.showToast(message: String, length: Int = 0) {
    when (length) {
        0 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        else -> Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

// Fragment toast
fun Fragment.showToast(message: String, length: Int = 0) {
    when (length) {
        0 -> Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
        else -> Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}

// converting the linkTreeList into the object
inline fun <reified T> convertToAny(json: Any): T = Gson().fromJson(Gson().toJson(json), T::class.java)

// converting the list of linkTreeList into the list
inline fun <reified T> convertToList(json: Any): List<T> {
    val generate = json as List<LinkedTreeMap<*, *>>
    return generate.map {
        convertToAny(it)
    }
}

fun ImageView.setImage(url: Any, isCenterCrop: Boolean = false) {
    when {
        isCenterCrop -> Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(this)
        else -> Glide.with(this).load(url).apply(RequestOptions()).error(R.drawable.error_image).into(this)
    }
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)!!
    else -> @Suppress("DEPRECATION") getSerializable(key) as T
}

/* -----------------  String type extension functions ----------------- */
fun String?.forHtmlText(): String {
    return when {
        this != null -> when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
            else -> Html.fromHtml(this).toString()
        }
        else -> ""
    }
}

