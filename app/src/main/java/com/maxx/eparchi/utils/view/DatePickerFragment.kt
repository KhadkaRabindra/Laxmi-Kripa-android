package com.maxx.eparchi.utils.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.maxx.eparchi.model.listener.DateListener
import java.util.*

class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener: DateListener? = null

    companion object {
        val FLAG_START_DATE = 0
        val FLAG_END_DATE = 1

        fun newInstance(listener: DateListener) : DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            return fragment
        }
    }

    private var flag = 0

    fun setFlag(i: Int) {
        flag = i
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener?.onDateSet(view, year, month, day, flag)
    }
}