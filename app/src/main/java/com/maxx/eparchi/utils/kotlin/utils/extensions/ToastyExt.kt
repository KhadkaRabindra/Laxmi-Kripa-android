package com.maxx.eparchi.utils.kotlin.utils.extensions

import androidx.fragment.app.Fragment

/**
 * Created by munnadroid on 6/9/17.
 */
fun android.content.Context.successToast(message: String) {
    es.dmoral.toasty.Toasty.success(this, message, android.widget.Toast.LENGTH_SHORT, true).show()
}

fun Fragment.successToast(message: String) {
    es.dmoral.toasty.Toasty.success(activity!!, message, android.widget.Toast.LENGTH_SHORT, true).show()
}


fun android.content.Context.errorToast(message: String) {
    es.dmoral.toasty.Toasty.error(this, message, android.widget.Toast.LENGTH_SHORT, true).show()
}

fun Fragment.errorToast(message: String) {
    es.dmoral.toasty.Toasty.success(activity!!, message, android.widget.Toast.LENGTH_SHORT, true).show()
}


fun android.content.Context.infoToast(message: String) {
    es.dmoral.toasty.Toasty.info(this, message, android.widget.Toast.LENGTH_SHORT, true).show()
}

fun Fragment.infoToast(message: String) {
    es.dmoral.toasty.Toasty.info(activity!!, message, android.widget.Toast.LENGTH_SHORT, true).show()
}


fun android.content.Context.warningToast(message: String) {
    es.dmoral.toasty.Toasty.warning(this, message, android.widget.Toast.LENGTH_SHORT, true).show()
}

fun Fragment.warningToast(message: String) {
    es.dmoral.toasty.Toasty.warning(activity!!, message, android.widget.Toast.LENGTH_SHORT, true).show()
}


fun android.content.Context.normalToast(message: String) {
    es.dmoral.toasty.Toasty.normal(this, message, android.widget.Toast.LENGTH_SHORT).show()
}

fun Fragment.normalToast(message: String) {
    es.dmoral.toasty.Toasty.normal(activity!!, message, android.widget.Toast.LENGTH_SHORT).show()
}


