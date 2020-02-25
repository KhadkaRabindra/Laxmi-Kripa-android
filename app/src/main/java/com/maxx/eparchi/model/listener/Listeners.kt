package com.maxx.eparchi.model.listener

import android.widget.DatePicker
import android.widget.TimePicker


interface OnFragmentSkipListener {
    fun onSkipPressed()
}

interface GameListener{
    fun onClosePressed(string: String)

    fun onGameCompleted(string: String)
}

interface DateListener{
    fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int, flag : Int)
}

interface TimeListener{
    fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int)
}