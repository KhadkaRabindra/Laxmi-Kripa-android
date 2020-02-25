package com.maxx.eparchi.utils.kotlin.utils.extensions

/**
 * Created by munnadroid on 5/29/17.
 */

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.RequiresApi

@TargetApi(Build.VERSION_CODES.M)
fun Context.colorRes(colorResId: Int): Int {
    if (beforeLollipop())
        return resources.getColor(colorResId)
    else return resources.getColor(colorResId, theme)
}

fun Context.intRes(intResId: Int): Int {
    return resources.getInteger(intResId)
}

fun Context.dimenRes(dimenResId: Int): Int {
    return resources.getDimensionPixelSize(dimenResId)
}

fun Context.stringArrayRes(arrayResId: Int): Array<String> {
    return resources.getStringArray(arrayResId)
}

fun Context.intArrayRes(arrayResId: Int): IntArray {
    return resources.getIntArray(arrayResId)
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.drawableRes(drawableResId: Int): Drawable {
    if (beforeLollipop())
        return resources.getDrawable(drawableResId)
    else return resources.getDrawable(drawableResId, theme)
}

fun Context.typefaceFromAssets(assetPathResId: Int): Typeface {
    return typefaceFromAssets(getString(assetPathResId))
}

fun Context.typefaceFromAssets(assetPath: String): Typeface {
    return Typeface.createFromAsset(assets, assetPath)
}

@TargetApi(Build.VERSION_CODES.M)
fun Fragment.colorRes(colorResId: Int): Int {
    if (beforeLollipop())
        return resources.getColor(colorResId)
    else return resources.getColor(colorResId, activity.theme)
}

fun Fragment.intRes(intResId: Int): Int {
    return resources.getInteger(intResId)
}

fun Fragment.dimenRes(dimenResId: Int): Int {
    return resources.getDimensionPixelSize(dimenResId)
}

fun Fragment.stringArrayRes(arrayResId: Int): Array<String> {
    return resources.getStringArray(arrayResId)
}

fun Fragment.intArrayRes(arrayResId: Int): IntArray {
    return resources.getIntArray(arrayResId)
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Fragment.drawableRes(drawableResId: Int): Drawable {
    if (beforeLollipop())
        return resources.getDrawable(drawableResId)
    else return resources.getDrawable(drawableResId, activity.theme)
}

fun Fragment.typefaceFromAssets(assetPathResId: Int): Typeface {
    return typefaceFromAssets(getString(assetPathResId))
}

fun Fragment.typefaceFromAssets(assetPath: String): Typeface {
    return Typeface.createFromAsset(activity.assets, assetPath)
}

@RequiresApi(Build.VERSION_CODES.M)
fun androidx.fragment.app.Fragment.colorRes(colorResId: Int): Int {
    if (beforeLollipop())
        return resources.getColor(colorResId)
    else return resources.getColor(colorResId, activity?.theme)
}

fun androidx.fragment.app.Fragment.intRes(intResId: Int): Int {
    return resources.getInteger(intResId)
}

fun androidx.fragment.app.Fragment.dimenRes(dimenResId: Int): Int {
    return resources.getDimensionPixelSize(dimenResId)
}

fun androidx.fragment.app.Fragment.stringArrayRes(arrayResId: Int): Array<String> {
    return resources.getStringArray(arrayResId)
}

fun androidx.fragment.app.Fragment.intArrayRes(arrayResId: Int): IntArray {
    return resources.getIntArray(arrayResId)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun androidx.fragment.app.Fragment.drawableRes(drawableResId: Int): Drawable {
    if (beforeLollipop())
        return resources.getDrawable(drawableResId)
    else return resources.getDrawable(drawableResId, activity?.theme)
}

fun androidx.fragment.app.Fragment.typefaceFromAssets(assetPathResId: Int): Typeface {
    return typefaceFromAssets(getString(assetPathResId))
}

fun androidx.fragment.app.Fragment.typefaceFromAssets(assetPath: String): Typeface {
    return Typeface.createFromAsset(activity?.assets, assetPath)
}

fun Int.toPx(context: Context): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()

fun Float.toPx(context: Context): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Int.toDp(activity: Activity): Int {
    val metrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(metrics)
    return Math.ceil((this / metrics.density).toDouble()).toInt()
}

fun Float.toDp(activity: Activity): Float {
    val metrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(metrics)
    return Math.ceil((this / metrics.density).toDouble()).toFloat()
}
