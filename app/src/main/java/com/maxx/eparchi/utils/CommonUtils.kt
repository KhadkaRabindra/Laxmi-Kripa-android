package com.maxx.eparchi.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.maxx.eparchi.BuildConfig
import org.jetbrains.anko.browse
import java.io.File
import java.text.SimpleDateFormat


object CommonUtils {
    private val TAG = CommonUtils::class.java.simpleName

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun fromHtml(str: String): Spanned {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY) // for 24 api and more
        } else {
            return Html.fromHtml(str) // or for older api
        }
    }


    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusView = activity.currentFocus
        if (focusView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    fun setMargins(view: View, margin: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(margin, margin, margin, margin)
            view.requestLayout()
        }
    }

    fun screen_size(context: Context): Double {

        var size = 0.0
        try {
            // Compute screen size
            val dm = context.resources.displayMetrics
            val screenWidth = dm.widthPixels / dm.xdpi
            val screenHeight = dm.heightPixels / dm.ydpi
            size = Math.sqrt(Math.pow(screenWidth.toDouble(), 2.0) +

                    Math.pow(screenHeight.toDouble(), 2.0))

        } catch (t: Throwable) {

        }

        return size

    }

    fun getAppVersion(context: Context): String {
        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        var version = pInfo!!.versionName
        if (TextUtils.isEmpty(version))
            version = BuildConfig.VERSION_NAME

        return version
    }

//    val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
//
//    fun validateEmail(emailStr: String): Boolean {
//        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
//        return matcher.find()
//    }

    fun isRegexValid(inputStr: String, regex: String): Boolean {
        return inputStr.matches(regex.toRegex())
    }


    fun format_twoDigit(number: Int): String {
        return String.format("%02d", number)
    }


    fun dir_exists(dir_path: String): Boolean {
        var ret = false
        val dir = File(dir_path)
        if (dir.exists() && dir.isDirectory)
            ret = true
        return ret
    }


    /**
     * check if service class is running or not

     * @param serviceClass
     * *
     * @return
     */
    fun isMyServiceRunning(serviceClass: Class<*>, context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }

        return false
    }

    fun ordinal(i: Int): String {
        val sufixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        when (i % 100) {
            11, 12, 13 -> return i.toString() + "th"
            else -> return i.toString() + sufixes[i % 10]
        }
    }

    fun getLastNCharacter(myString: String, n: Int): String {
        if (myString.length > n)
            return myString.substring(myString.length - n)
        else
            return myString
    }


    /**
     * copy text to clipboard

     * @param text
     * *
     * @param context
     */
    fun copyToClipboard(text: String, label: String, context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.primaryClip = clip

        toast(context, label)
    }


    fun show_keyboard_in_view(view: View) {
        view.requestFocus()
        view.postDelayed({
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }, 300)
    }

    fun startApp(context: Context?, packageName: String?) {
        try {
            var intent: Intent? = context?.packageManager?.getLaunchIntentForPackage(packageName)
            if (intent == null) {
                // Bring user to the market or let them choose an app?
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=" + packageName)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        } catch (anfe: android.content.ActivityNotFoundException) {
            context?.browse("https://play.google.com/store/apps/details?id=$packageName")
        }
    }


    fun launchPlayStore(context: Context?, packageName: String?) {
        var intent: Intent? = null
        try {
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("market://details?id=" + packageName)
            context?.startActivity(intent)
        } catch (anfe: android.content.ActivityNotFoundException) {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)))
        }

    }

    /**
     * parse date to display in e-statement view
     */
    fun parseEStatementDate(input: String?): String? {
        val originalFormat = SimpleDateFormat("MM/yyyy")
        val originalDate = originalFormat.parse(input)
        val targetFormat = SimpleDateFormat("MMMM yyyy")

        return targetFormat.format(originalDate)
    }

    /*public fun setUpKeyValueForCrashLog() {
        Crashlytics.setString("ENVIRONMENT", BuildConfig.BUILD_TYPE)
        if (UserInfo.fullName.isEmpty() && UserInfo.phoneNumber.isEmpty()) {
            Crashlytics.setUserIdentifier("")
            Crashlytics.setString("DEVICE_KEY", "")
        } else {
            Crashlytics.setUserIdentifier(UserInfo.phoneNumber)
            Crashlytics.setString("DEVICE_KEY", UserInfo.deviceKey)
        }
    }*/

}
