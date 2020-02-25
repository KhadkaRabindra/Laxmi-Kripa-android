package com.maxx.eparchi.utils.kotlin.utils.extensions

/**
 * Created by munnadroid on 5/29/17.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}