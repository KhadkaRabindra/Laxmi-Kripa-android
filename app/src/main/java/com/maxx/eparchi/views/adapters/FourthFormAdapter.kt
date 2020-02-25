package com.maxx.eparchi.views.adapters

import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.maxx.eparchi.R
import com.maxx.eparchi.model.FourthFormData
import java.util.*

class FourthFormAdapter(private val context: Context?, private val arrayList: List<FourthFormData>?) :
    RecyclerView.Adapter<FourthFormAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_fourth_form, parent, false)
        return FourthFormAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arrayList?.get(position)

        holder.startTimeEditText.isFocusable = false
        holder.startTimeEditText.isClickable = true

        holder.endTimeEditText.isFocusable = false
        holder.endTimeEditText.isClickable = true

        holder.idTextView.text = data?.id ?: ""
        holder.startTimeEditText.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    holder.startTimeEditText.setText(
                        "$selectedHour:$selectedMinute"
                    )
                    data?.startTime = "$selectedHour:$selectedMinute"
                }, hour, minute, true
            )//Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        holder.endTimeEditText.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    holder.endTimeEditText.setText(
                        "$selectedHour:$selectedMinute"
                    )
                    data?.endTime = "$selectedHour:$selectedMinute"
                }, hour, minute, true
            )//Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.idTextView)
        lateinit var idTextView: TextView

        @BindView(R.id.startTimeEditText)
        lateinit var startTimeEditText: EditText
        @BindView(R.id.endTimeEditText)
        lateinit var endTimeEditText : EditText
        @BindView(R.id.totalBundlesEditText)
        lateinit var totalBundlesEditText : EditText


        init {
            ButterKnife.bind(this, itemView)
        }
    }
}