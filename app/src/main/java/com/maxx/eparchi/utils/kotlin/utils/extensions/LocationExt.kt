package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.maxx.eparchi.utils.Constants
import okhttp3.Interceptor

val MOBILE_PASSCODE_CHECK_END_POINT = "mobile/passcode/check"
val TRANSACTION_OTP_VERIFY_END_POINT = "transaction/otp/verify"
val TRANSACTION_ATX_BILL_PAYMENT_END_POINT = "transaction/atx/billpayment/pay"
val TRANSACTION_WALLET_TO_WALLET_OTP_REQUEST_END_POINT = "transaction/wallettowallet/otprequest"
val MOBILE_TAG_LOGIN_LOCATION_END_POINT = "mobile/tagLoginLocation"


fun Context.isLocationPermissionGranted(chain: Interceptor.Chain?): Int {
    val urlString = chain?.call()?.request()?.url().toString()
    if (urlString == getFullEndPoint(MOBILE_PASSCODE_CHECK_END_POINT) || urlString == getFullEndPoint(
            TRANSACTION_OTP_VERIFY_END_POINT
        )
            || urlString == getFullEndPoint(TRANSACTION_ATX_BILL_PAYMENT_END_POINT) || urlString == TRANSACTION_WALLET_TO_WALLET_OTP_REQUEST_END_POINT
            || urlString == getFullEndPoint(MOBILE_TAG_LOGIN_LOCATION_END_POINT)
    ) {
        val flag = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (flag)
            return 1
        else
            return 2
    } else
        return 0
}

fun getFullEndPoint(string: String): String? {
    return Constants.BASE_URL + string
}