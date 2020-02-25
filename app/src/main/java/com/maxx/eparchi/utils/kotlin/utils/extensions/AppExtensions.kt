package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.maxx.eparchi.R
import com.maxx.eparchi.utils.view.NoDefaultARSpinner

/**
 * Created by munnadroid on 10/29/17.
 */


fun List<EditText>.makeEmpty() {
    this.filterNotNull()
        .forEach { it.makeEmpty() }
}

fun EditText.makeEmpty() {
    this.textValue = ""
}

var EditText.textValue: String
    get() = text.toString()
    set(v) {
        setText(v)
    }

var TextView.textValue: String
    get() = text.toString()
    set(v) {
        text = v
    }


fun NoDefaultARSpinner.setSpinnerAdapter(
    dataList: ArrayList<String>?,
    prompt: String = "Select",
    rowLayout: Int? = R.layout.spinner_row_selected,
    dropDownLayout: Int? = R.layout.spinner_row_dropdown
) {

    this.prompt = prompt

    val spinnerAdapter = ArrayAdapter(context, rowLayout!!, dataList)
    if (dropDownLayout != null)
        spinnerAdapter.setDropDownViewResource(dropDownLayout)

    adapter = spinnerAdapter
}


fun removeCountryCodeFromPhoneNumber(countryCode: String?, phoneNumber: String?): String {
    if (phoneNumber?.startsWith(countryCode.toString())!!) {
        phoneNumber.replace(countryCode!!, "")
    }
    return phoneNumber
}