package com.maxx.eparchi.utils.retrofit

import android.content.Context

object EAttendanceServiceProvider {
    fun provideEAttendanceService(applicationContext: Context): ApiService {
        return ApiService.Factory.create(applicationContext)
    }
}