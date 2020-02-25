package com.maxx.eparchi.utils.kotlin.utils.extensions

import android.text.InputFilter
import android.widget.EditText


/**
 * Created by anp01 on 3/26/18.
 */
/**
 * set empty value to edittext
 */
fun EditText.resetEditText() {
    this.setText("")
}

/**
 * restrict emoji in editext
 */
var EMOJI_FILTER = InputFilter { source, start, end, dest, dstart, dend ->
    for (index in start until end) {

        val type = Character.getType(source[index])

        if (type == Character.SURROGATE.toInt()) {
            return@InputFilter ""
        }
    }
    null
}

/**
 * avoid graphical emoji in edittext
 */
fun Array<EditText>.ignoreEmojiInEditText() {
    for (editText in this){
        val editFilters = editText.filters;
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = EMOJI_FILTER
        editText.filters = newFilters;
    }
}

/**
 * avoid graphical emoji in autocompleteTextView
 */
/*fun Array<InstantAutocompleteTextView>.ignoreEmojiInEditText() {
    for (editText in this)
        editText.filters = arrayOf(EMOJI_FILTER)
}*/

/**
 * make text capital in edittext
 */
fun Array<EditText>.makeInputCapital() {
    for (editText in this){
        val editFilters = editText.filters;
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = InputFilter.AllCaps()
        editText.filters = newFilters;
    }
}

fun Array<EditText>.makeInputCapitalAndAvoidEmojis() {
    for (editText in this)
        editText.filters = arrayOf(InputFilter.AllCaps(), EMOJI_FILTER)
}


/*set max length*/
fun EditText.setMaxLength(maxLength : Int) {
    /*for (editText in this){
        val editFilters = editText.filters;
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = InputFilter.LengthFilter(maxLength)
        editText.filters = newFilters;


    }*/

    /*val editFilters = this.filters;
    val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
    System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
    newFilters[editFilters.size] = InputFilter.LengthFilter(maxLength)
    this.filters = newFilters;*/
    this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

}


fun EditText.checkForMoreThanTwoSpaces(): Boolean{
    val str = this.text.toString()
    val count = str.length - str.replace(" ", "").length
    if (str == " ")
        return false
    else if (count == 1 || count == 0)
        return true
    else
        return false
}