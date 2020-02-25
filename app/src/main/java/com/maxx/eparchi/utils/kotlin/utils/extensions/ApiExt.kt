package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.os.Build

fun beforeMarshmallow(): Boolean = isOlderVersionThen(23)
fun marshmallowOrNewer(): Boolean = isOnVersionOrNewer(23)
fun beforeLollipop(): Boolean = isOlderVersionThen(21)
fun lollipopOrNewer(): Boolean = isOnVersionOrNewer(21)
fun beforeKitkat(): Boolean = isOlderVersionThen(19)
fun kitkatOrNewer(): Boolean = isOnVersionOrNewer(19)
fun beforeIcs(): Boolean = isOlderVersionThen(14)
fun icsOrNewer(): Boolean = isOnVersionOrNewer(14)
fun beforeVersion(version: Int): Boolean = isOlderVersionThen(version)
fun versionOrNewer(version: Int): Boolean = isOnVersionOrNewer(version)
fun currentVersion(): Int = Build.VERSION.SDK_INT

/*
 * -----------------------------------------------------------------------------
 *  Private methods
 * -----------------------------------------------------------------------------
 */
fun isOlderVersionThen(version: Int) = Build.VERSION.SDK_INT < version

fun isOnVersionOrNewer(version: Int) = Build.VERSION.SDK_INT >= version