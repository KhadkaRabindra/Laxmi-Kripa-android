package com.maxx.eparchi.views.splash

import android.content.Intent
import com.maxx.eparchi.views.unused.home.HomeActivity
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseActivity
import com.maxx.eparchi.views.login.LoginActivity

class SplashActivity : BaseActivity() {
    override val layoutResourceId: Int
        get() = R.layout.activity_splash

    override fun initView() {
        makeActivityFullScreen()
        val background = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    Thread.sleep((2 * 1000).toLong())
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {

                }
            }
        }
        // start thread
        background.start()
    }

}
