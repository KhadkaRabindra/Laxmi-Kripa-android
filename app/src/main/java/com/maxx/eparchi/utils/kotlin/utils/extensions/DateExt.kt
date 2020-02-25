package com.maxx.eparchi.utils.kotlin.utils.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by anp01 on 7/26/17.
 */

/**
 * get todays date in yyyy/MM/dd format to get txn ic_history
 */
fun getTodaysDate(format: String? = "yyyy/MM/dd"): String? {
    val cal = Calendar.getInstance()
    return getFormattedDate(cal.time, format)
}

/**
 * format date to yyyy/MM/dd
 */
fun getFormattedDate(date: Date?, format: String? = "yyyy/MM/dd"): String? {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(date)
}

/**
 * get from date in yyyy/MM/dd format to get txn ic_history
 */
fun getFromDate(): String? {
    val cal = Calendar.getInstance()
    val fromDate = Date(cal.timeInMillis - 2592000000L)// for 30 days = 30*24*60*60*1000
    return getFormattedDate(fromDate)
}

fun getTxnTime(dateTime: String?): String? {//2017-07-26T12:39:16.69
    val timeFormat = SimpleDateFormat("HH:mm")
    return timeFormat.format(convertTxnResponseStringToDate(dateTime!!))
}


fun convertTxnResponseStringToDate(dateTime: String?): Date? {
    val responseDateFromat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return responseDateFromat.parse(dateTime)
}

fun getTxnDate(dateTime: String?): String? {
    val outputFormat = "MMMM dd"
    val parsedDateFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return parsedDateFormat.format(convertTxnResponseStringToDate(dateTime))
}

fun getTxnDateForRecentTransaction(dateTime: String?): String? {
    val outputFormat = "MMM dd"
    val parsedDateFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return parsedDateFormat.format(convertTxnResponseStringToDate(dateTime))
}


fun CompareDates(startDate: String?, endDate: String?): Boolean {
    val sdf = SimpleDateFormat("yyyy/MM/dd")
    val _startDate = sdf.parse(startDate) as Date
    val _endDate = sdf.parse(endDate) as Date
    return _startDate.compareTo(_endDate) <= 0
}

fun Long.toDateString(): String{
    val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    return dateFormat.format(this)
}

fun getCurrDay(): Int{
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd")
    return dateFormat.format(cal.timeInMillis).toInt()
}

fun getCurrMonth(): Int{
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MM")
    return dateFormat.format(cal.timeInMillis).toInt()-1
}

fun getCurrYear(): Int{
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy")
    return dateFormat.format(cal.timeInMillis).toInt()
}

fun getMaxYear(): Int{
    return getCurrYear() +1
}

fun getDateInCorrectFormat(from : String) : String?{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var sourceDate : Date? = null
    try {
        sourceDate = dateFormat.parse(from);
    } catch ( e : ParseException) {
        e.printStackTrace();
    }

    val targetFormat = SimpleDateFormat("MM/dd/yyyy");
    return targetFormat.format(sourceDate);
}