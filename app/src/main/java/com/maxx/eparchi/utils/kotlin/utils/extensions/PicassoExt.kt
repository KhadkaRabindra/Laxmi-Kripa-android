package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.widget.ImageView
import com.jakewharton.picasso.OkHttp3Downloader
import com.maxx.eparchi.BuildConfig
import com.maxx.eparchi.R
import com.maxx.eparchi.model.UserInfo
import com.maxx.eparchi.utils.Constants
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

fun ImageView.loadImageWithDeviceHeader(url: String?, placeholder: Int = R.drawable.ic_launcher_background) {
    val client = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = chain.request().newBuilder()
                    .addHeader("DeviceKey", UserInfo.deviceKey)
                    ?.header("Content", "application/json")
                    ?.header("DeviceType", Constants.DEVICE_TYPE)
                    ?.header("BuildVersion", BuildConfig.VERSION_NAME)
                    ?.header("ApiVersion", Constants.API_VERSION)
                    ?.build()
                return chain.proceed(newRequest)
            }
        })
        .build()

    val picasso = Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()

    picasso.load(url)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadImageWithDeviceHeaderGetPicasso(): Picasso? {
    val client = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = chain.request().newBuilder()
                    .addHeader("DeviceKey", UserInfo.deviceKey)
                    ?.header("Content", "application/json")
                    ?.header("DeviceType", Constants.DEVICE_TYPE)
                    ?.header("BuildVersion", BuildConfig.VERSION_NAME)
                    ?.header("ApiVersion", Constants.API_VERSION)
                    ?.build()
                return chain.proceed(newRequest)
            }
        })
        .build()

    val picasso = Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()
    return picasso

}


//from mcoupon
/**
 * load image from url
 */
fun ImageView.loadImageFromUrl(imageUrl: String?) {
    try {
        Picasso.with(context).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(this)
    } catch (e: Exception) {
    }
}

/**
 * load image without place holder
 */
fun ImageView.loadImageNoPlaceholder(imageUrl: String?) {
    try {
        Picasso.with(context).load(imageUrl).into(this)
    } catch (e: Exception) {
    }
}

/**
 * load image without place holder
 */
fun ImageView.loadImageWithPlaceHolder(imageUrl: String?, placeholder : Int) {
    try {
        Picasso.with(context).load(imageUrl).placeholder(placeholder).into(this)
    } catch (e: Exception) {
    }
}

/**
 * load thumbnail without place holder
 */
fun ImageView.loadThumbnailNoPlaceholder(imageUrl: String?) {
    try {
        Picasso.with(context)
            .load(imageUrl)
            .resize(400, 400)
            .onlyScaleDown()
            .centerCrop()
            .into(this)
    } catch (e: Exception) {
    }
}

/**
 * load thumbnail without place holder
 */
fun ImageView.loadThumbnailNoPlaceholderNoCrop(imageUrl: String?) {
    try {
        Picasso.with(context)
            .load(imageUrl)
            .resize(400, 400)
            .onlyScaleDown()
            .into(this)
    } catch (e: Exception) {
    }
}

/**
 * load thumbnail without place holder
 */
fun ImageView.loadThumbnailWithPlaceholder(imageUrl: String?) {
    try {
        Picasso.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .resize(200, 200)
            .onlyScaleDown()
            .centerCrop()
            .into(this)
    } catch (e: Exception) {
    }
}