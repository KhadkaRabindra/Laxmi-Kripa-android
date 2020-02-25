package com.maxx.eparchi

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import com.maxx.eparchi.utils.retrofit.ApiService
import com.maxx.eparchi.utils.retrofit.EAttendanceServiceProvider


class MyApplication : MultiDexApplication() {

    lateinit var mApiService: ApiService

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        mApiService = EAttendanceServiceProvider.provideEAttendanceService(this)
        // For gson support module
        Kotpref.gson = Gson()
    }

    fun getAPIService(): ApiService {
        return mApiService
    }

}
