package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.chibatching.kotpref.bulk
import com.maxx.eparchi.model.UserInfo
import com.maxx.eparchi.views.splash.SplashActivity

/**
 * Created by anp01 on 9/5/17.
 */
fun Context.doLogOut() {
    /*clear All Notifications*/
    val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancelAll()

    UserInfo.bulk {
        isLoggedIn = false
        fcmTokenSendStatus = false
        phoneNumber = ""
    }

    val intent = Intent(this, SplashActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    (this as Activity).finish()
}