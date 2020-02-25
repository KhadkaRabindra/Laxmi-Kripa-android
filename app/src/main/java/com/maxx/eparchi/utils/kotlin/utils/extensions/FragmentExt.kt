package com.maxx.eparchi.utils.kotlin.utils.extensions

/**
 * Created by munnadroid on 5/29/17.
 */

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment


/**
 * Created by Jacek Kwiecień on 01.04.15.
 */

/**
 * Start [Activity] with optional extras.
 * @author Jacek Kwiecień
 * @param extras optional extras provided to started activity
 */
inline fun <reified T : Activity> Fragment.launchActivity(options: Bundle? = null,
                                                          noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(context!!)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

/**
 * Start [Activity] for result with optional extras.
 * @author Jacek Kwiecień
 * @param requestCode code returned [Activity.onActivityResult]
 * @param extras optional extras provided to started activity
 */
inline fun <reified T : Activity> Fragment.launchActivityForResult(requestCode: Int, extras: Bundle? = null) {
    val intent = newIntent<T>(context!!)
    if (extras != null) intent.putExtras(extras)
    startActivityForResult(intent, requestCode)
}

/**
 * Start [Activity] with optional extras.
 * @author Jacek Kwiecień
 * @param extras optional extras provided to started activity
 */
@RequiresApi(Build.VERSION_CODES.M)
inline fun <reified T : Activity> android.app.Fragment.launchActivity(extras: Bundle? = null) {
    val intent = newIntent<T>(context)
    if (extras != null) intent.putExtras(extras)
    startActivity(intent)
}

/**
 * Start [Activity] for result with optional extras.
 * @author Jacek Kwiecień
 * @param requestCode code returned [Activity.onActivityResult]
 * @param extras optional extras provided to started activity
 */
@RequiresApi(Build.VERSION_CODES.M)
inline fun <reified T : Activity> android.app.Fragment.launchActivityForResult(requestCode: Int, extras: Bundle? = null) {
    val intent = newIntent<T>(context)
    if (extras != null) intent.putExtras(extras)
    startActivityForResult(intent, requestCode)
}
