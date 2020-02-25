package com.maxx.eparchi.utils.kotlin.utils.extensions

/**
 * Created by maxx on 4/5/18.
 */
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.maxx.eparchi.R
import com.maxx.eparchi.utils.Constants
import com.maxx.eparchi.utils.Constants.NotificationChannelId
import com.maxx.eparchi.utils.Constants.NotificationChannelName
import com.maxx.eparchi.views.splash.SplashActivity
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Create Notification to support all OS Versions
 */
fun Context.createNotification(messageBody: String?, messageTitle: String?, messageType: String?, imageURL: String?) {
    var mNotificationBuilder: NotificationCompat.Builder? = null
    var mNotificationBuilderSummary: NotificationCompat.Builder? = null
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mNotificationBuilder = NotificationCompat.Builder(this, NotificationChannelId)
                .setChannelId(NotificationChannelId)
                .setUpBuilder(this, messageTitle, messageType)
                .handleMessageAndImage(imageURL, messageBody, messageTitle)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)

        notificationManager.createNotificationChannel(getNotificationChannel())

        mNotificationBuilderSummary = NotificationCompat.Builder(this, NotificationChannelId)
                .setChannelId(NotificationChannelId)
                .setUpBuilder(this, messageTitle, messageType, false)

        mNotificationBuilder.setGroup(Constants.NOTIFICATION_GROUP_NAME)
        mNotificationBuilderSummary.setGroupSummary(true)?.setGroup(Constants.NOTIFICATION_GROUP_NAME)
        notificationManager.notify(Constants.SUMMARY_NOTIFCATION_ID, mNotificationBuilderSummary.build())
    } else {
        mNotificationBuilder = NotificationCompat.Builder(this)
        mNotificationBuilder.setUpBuilder(this, messageTitle, messageType)
                .handleMessageAndImage(imageURL, messageBody, messageTitle)
    }
    notificationManager.notify(getUniqueID(), mNotificationBuilder?.build())
}

/**
 * Set up Notification Compat Builder
 */
fun NotificationCompat.Builder.setUpBuilder(context: Context?, messageTitle: String?, messageType: String?, enableSound: Boolean = true): NotificationCompat.Builder {
    setSmallIcon(R.mipmap.ic_launcher)
            //.setBadgeIconType(R.mipmap.ic_launcher)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setContentTitle(messageTitle)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            .setContentIntent(context.getResultIntent(messageType))
            .setVibrate(longArrayOf(1000, 1000))
    if (enableSound)
        this.setSound(getNotificationSoundURI())
    return this
}

/**
 * Handle for message and image
 */
fun NotificationCompat.Builder.handleMessageAndImage(imageURL: String?, messageBody: String?, messageTitle: String?): NotificationCompat.Builder {
    if (!imageURL.isNullOrEmpty()) {
        val bitmap = imageURL?.getBitmapFromUrl()
        setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap).setSummaryText(messageBody))
    } else {
        setContentText(messageBody)
                .setStyle(NotificationCompat.BigTextStyle()
                        .setBigContentTitle(messageTitle)
                        .bigText(messageBody))
    }
    return this
}

/**
 * Get Notification Sound URI
 */
fun getNotificationSoundURI(): Uri? {
    return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
}

/**
 * Get result intent
 */
fun Context.getResultIntent(messageType: String?): PendingIntent? {
    val intent = Intent(this, SplashActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
}

fun isApplicationRunningInBackground(): Boolean {
    val myProcess = ActivityManager.RunningAppProcessInfo()
    ActivityManager.getMyMemoryState(myProcess)
    return myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
}

/**
 * Get Notification Channel
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getNotificationChannel(): NotificationChannel? {
    // The user-visible description of the channel.
    val description = "Notifications regarding our products"
    val importance = NotificationManager.IMPORTANCE_MAX as Int
    val mChannel = NotificationChannel(NotificationChannelId, NotificationChannelName, importance)
    // Configure the notification channel.
    mChannel.description = description
    // Sets whether notifications posted to this channel should display notification lights
    mChannel.enableLights(true)
    // Sets whether notification posted to this channel should vibrate.
    mChannel.enableVibration(true)
    // Sets whether notifications posted to this channel appear on the lockscreen or not
    mChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    // Sets the notification light color for notifications posted to this
    // channel, if the device supports this feature.
    mChannel.lightColor = Color.RED
    return mChannel
}

/**
 * Get Bitmap from url
 */
fun String.getBitmapFromUrl(): Bitmap? {
    try {
        val url = URL(/*Constants.BASE_URL + */this)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        return BitmapFactory.decodeStream(input)

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

/**
 * Generates unique id for each notification
 */
fun getUniqueID(): Int {
    val now = Date()
    return Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.US).format(now))
}

object NotificationID {
    private val c = AtomicInteger(0)
    val id: Int
        get() = c.incrementAndGet()
}