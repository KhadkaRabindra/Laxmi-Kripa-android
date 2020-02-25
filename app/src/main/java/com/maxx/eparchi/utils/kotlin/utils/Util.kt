package com.maxx.eparchi.utils.kotlin.utils

import android.content.Context
import android.provider.Settings
import com.chibatching.kotpref.bulk
import com.maxx.eparchi.model.LoginResponse
import com.maxx.eparchi.model.UserInfo
import java.util.*

class Util {

    companion object {

        fun getDeviceUniqueId(context: Context?): String {
            var androidId: String = ""
            try {
                androidId = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (e: Exception) {
                e.printStackTrace()
                androidId = UUID.randomUUID().toString()
            }

            return androidId
        }

        /**
         * save data to sharedpref from user authentification api
         */
        fun saveUserInfoFromResponse(signupResponse: LoginResponse?) {
            UserInfo.bulk {
                isLoggedIn = true
                token = signupResponse?.token as String
            }
        }
    }
}