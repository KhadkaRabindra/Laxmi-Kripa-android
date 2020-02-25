package com.maxx.eparchi.views.adapters

import android.app.TimePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.maxx.eparchi.R
import com.maxx.eparchi.model.FirstFormData
import java.util.*


class FirstFormAdapter(private val context: Context?, val arrayList: List<FirstFormData>?) :
    RecyclerView.Adapter<FirstFormAdapter.ViewHolder>() {

    enum class TYPE{
        OPERATOR, HELPER, COMPANY_CONTROLLER, WIRE_TYPE, MACHINE_NOT_WORKING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstFormAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_first_form, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList?.size!!
    }

    override fun onBindViewHolder(holder: FirstFormAdapter.ViewHolder, position: Int) {
        val data = arrayList?.get(position)
        holder.bindData(context, data)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.idTextView)
        lateinit var idTextView: TextView

        @BindView(R.id.startTimeEditText)
        lateinit var startTimeEditText: EditText
        @BindView(R.id.operatorEditText)
        lateinit var operatorEditText: EditText
        @BindView(R.id.helperEditText)
        lateinit var helperEditText: EditText
        @BindView(R.id.companyEditText)
        lateinit var companyEditText: EditText
        @BindView(R.id.wireTypeEditText)
        lateinit var wireTypeEditText: EditText
        @BindView(R.id.machineNotWorkingEditText)
        lateinit var machineNotWorkingEditText: EditText

        init {
            ButterKnife.bind(this, itemView)
            operatorEditText.addTextChangedListener(MyTextWatcher(TYPE.OPERATOR))
            helperEditText.addTextChangedListener(MyTextWatcher(TYPE.HELPER))
            companyEditText.addTextChangedListener(MyTextWatcher(TYPE.COMPANY_CONTROLLER))
            wireTypeEditText.addTextChangedListener(MyTextWatcher(TYPE.WIRE_TYPE))
            machineNotWorkingEditText.addTextChangedListener(MyTextWatcher(TYPE.MACHINE_NOT_WORKING))
        }

        fun bindData(context: Context?, data: FirstFormData?) {
            startTimeEditText.isFocusable = false
            startTimeEditText.isClickable = true

            idTextView.text = data?.id ?: ""
            startTimeEditText.setOnClickListener {
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                        data?.startTime = "$selectedHour:$selectedMinute"
                        startTimeEditText.setText(data?.startTime)
                    }, hour, minute, true
                )//Yes 24 hour time
                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            }

            startTimeEditText.setText(data?.startTime)
            operatorEditText.setText(data?.operator)
            helperEditText.setText(data?.helper)
            companyEditText.setText(data?.companyController)
            wireTypeEditText.setText(data?.wireType)

        }



        inner class MyTextWatcher(var type: TYPE) : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val d = s.toString()
                if (type == TYPE.OPERATOR){
                    arrayList?.get(adapterPosition)?.operator = d
                }else if (type == TYPE.HELPER){
                    arrayList?.get(adapterPosition)?.helper = d
                }else if (type == TYPE.COMPANY_CONTROLLER){
                    arrayList?.get(adapterPosition)?.companyController = d
                }else if (type == TYPE.WIRE_TYPE){
                    arrayList?.get(adapterPosition)?.wireType = d
                }
            }
        }
    }
}