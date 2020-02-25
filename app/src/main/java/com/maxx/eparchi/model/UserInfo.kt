package com.maxx.eparchi.model

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {
    var isLoggedIn by booleanPref(default = false)
    var token by stringPref(default = "")

    var deviceKey by stringPref(default = "")
    var refreshToken by stringPref(default = "")
    var userImageURL by stringPref(default = "")
    var oldDeviceIdentifier by stringPref(default = "")
    var isNotificationActive by booleanPref(default = false)
    var deviceIdentifier by stringPref(default = "")
    var fullName by stringPref(default = "")
    var phoneNumber by stringPref(default = "")
    var hasPasscode by booleanPref(default = false)
    var fcmToken by stringPref(default = "")
    var fcmTokenSendStatus by booleanPref(default = false)

    /*For Language*/
    var languageVersion by stringPref(default = "0")
}
