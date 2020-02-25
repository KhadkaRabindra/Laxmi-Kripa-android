package com.maxx.eparchi.utils.kotlin.utils.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

/**
 * Created by maxx on 12/28/17.
 */

fun convertCommaSeparatedStringToDouble(string: String?): String? {
    //return string.replace(",", "")
    val nf = NumberFormat.getInstance(Locale.ENGLISH) // make locale-specific
    var s2 : String? = ""
    try {
        val i1 = nf.parse(string).toDouble()
        s2 = i1.toString()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return s2.toString()

}

fun convertDoubleToCommaSeparatedString(double: Double?): String? {
    /*val formatter = DecimalFormat("#,##0.00")
    return formatter.format(double)*/

    val nf = NumberFormat.getNumberInstance(Locale.ENGLISH)
    val formatter = nf as DecimalFormat
    formatter.applyPattern("#,##0.00")
    return formatter.format(double)
}