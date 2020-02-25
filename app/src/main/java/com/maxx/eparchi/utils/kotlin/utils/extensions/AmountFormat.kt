package com.maxx.eparchi.utils.kotlin.utils.extensions

import java.util.*


fun formatToTwoDecimalDigits(amount: Double?): String? {
    return String.format(Locale.US, "%,.2f", amount)
}