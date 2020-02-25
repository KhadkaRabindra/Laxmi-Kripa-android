package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View

/**
 * Created by munnadroid on 5/29/17.
 */


fun View.showIf(show: Boolean) {
    if (show) {
        show()
    } else {
        hide()
    }
}

fun View.show(): Unit {
    visibility = View.VISIBLE
}

fun View.hide(): Unit {
    visibility = View.GONE
}

fun View.invisible(): Unit {
    visibility = View.INVISIBLE
}

fun View.openBrowser(context: Context, url: String) {
    try {
        val uris = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.toggleVisibility(): Unit = if (visibility == View.VISIBLE) hide() else show()
