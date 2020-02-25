package com.maxx.eparchi.utils.view

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import com.maxx.eparchi.BuildConfig
import com.maxx.eparchi.R
import com.maxx.eparchi.utils.CommonUtils

/**
 * Created by maxx on 4/25/18.
 */
class Alerts {

    companion object {
        fun displayErrorInternal(context: Context, msg: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.new_version_is_available)).setMessage(msg).setCancelable(false).
                    setPositiveButton(context.getString(R.string.update),
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                        CommonUtils.launchPlayStore(context, BuildConfig.APPLICATION_ID)
                    })
            val alert = builder.create()
            alert.show()
        }

        fun register(activity: Activity) {
            AlertReceiver.register(activity)
        }

        fun unregister(activity: Activity) {
            AlertReceiver.unregister(activity)
        }

        fun displayError(context: Context, msg: String) {
            val intent = Intent("MyApplication.INTENT_DISPLAYERROR")
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            context.sendOrderedBroadcast(intent, null)
        }
    }

    private class AlertReceiver private constructor(activity: Activity) : BroadcastReceiver() {
        private val activityContext: Context

        init {
            activityContext = activity
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            abortBroadcast()
            val msg = intent?.getStringExtra(Intent.EXTRA_TEXT)
            displayErrorInternal(activityContext, msg!!)
        }

        companion object {
            private var registrations: HashMap<Activity, AlertReceiver>? = null

            init {
                registrations = HashMap()
            }

            internal fun register(activity: Activity) {
                val receiver = AlertReceiver(activity)
                activity.registerReceiver(receiver, IntentFilter("MyApplication.INTENT_DISPLAYERROR"))
                registrations!![activity] = receiver
            }

            internal fun unregister(activity: Activity) {
                val receiver = registrations!![activity]
                if (receiver != null) {
                    activity.unregisterReceiver(receiver)
                    registrations!!.remove(activity)
                }
            }
        }
    }
}