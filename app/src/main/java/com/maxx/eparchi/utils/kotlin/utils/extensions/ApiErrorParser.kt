package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

fun parseError(errorBody: ResponseBody?): String? {
    /*var errorMessage = ""
    try {
        val errorStr = errorBody?.string()
        val jsonArray = JSONArray(errorStr)
        for (i in 0..jsonArray.length()) {
            if (i == 0) {
                val jsonObject = jsonArray.getJSONObject(i)
                errorMessage = jsonObject.getString("Message")
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return errorMessage*/

    var errorMessage = ""
    try {
        val errorStr = errorBody?.string()
        val jsonObject = JSONObject(errorStr)
        errorMessage = jsonObject.getString("error")
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return errorMessage
}

fun parseError(t: Throwable?): String? {
    if (t is HttpException) {
        Log.i("TEST_", "")
        val body = (t as HttpException).response().errorBody()
        return parseError(body as ResponseBody)
    } else {
        var errorMessage: String? = t?.message
        if (errorMessage.isNullOrEmpty())
            errorMessage = "Network error. Try again."
        else if (errorMessage?.contains("merchantrademoney.com") as Boolean)
            errorMessage = "Failed to connect to server."
        return errorMessage as String
    }
}