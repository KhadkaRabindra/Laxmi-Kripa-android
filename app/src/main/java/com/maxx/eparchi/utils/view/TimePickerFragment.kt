package com.maxx.eparchi.utils.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.maxx.eparchi.model.listener.TimeListener
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    var listener: TimeListener? = null

    companion object {
        fun newInstance(listener : TimeListener) : TimePickerFragment{
            val fragment = TimePickerFragment()
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        //Create and return a new instance of TimePickerDialog
        return TimePickerDialog(
            activity, this, hour, minute,
            DateFormat.is24HourFormat(activity)
        )

    }

    //onTimeSet() callback method
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        //etTime.setText("$hourOfDay:$minute")
        listener?.onTimeSet(view, hourOfDay, minute)
    }
}