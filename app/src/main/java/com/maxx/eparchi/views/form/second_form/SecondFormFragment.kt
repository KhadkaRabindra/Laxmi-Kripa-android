package com.maxx.eparchi.views.form.second_form

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.views.form.fourth_form.FourthForm1Fragment
import com.maxx.eparchi.views.form.third_form.ThirdFormFragment
import com.maxx.eparchi.views.unused.home.HomeActivity
import kotlinx.android.synthetic.main.second_form_end_time_before.*
import kotlinx.android.synthetic.main.second_form_start_first_time.*
import kotlinx.android.synthetic.main.second_form_start_time_before.*
import kotlinx.android.synthetic.main.second_form_weight.*
import java.util.*
import kotlin.collections.ArrayList

class SecondFormFragment : BaseFragment(), TimePickerDialog.OnTimeSetListener {

    var timeType: TIME_TYPE? = null

    enum class TIME_TYPE {
        START_TIME, END_TIME
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (timeType == TIME_TYPE.END_TIME) {
            endTimeEditText.setText(
                "$hourOfDay:$minute"
            )
        } else {
            startTimeEditText.setText(
                "$hourOfDay:$minute"
            )
        }
    }

    companion object {
        fun newInstance(): SecondFormFragment {
            return SecondFormFragment()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_second_form

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is HomeActivity)
            (activity as HomeActivity).setupToolbarTitle("Form 2")

        setUpWeightMachineSpinner()
        setUpBundleTypeSpinner()
        setUpBundleNumberSpinner()

        setUpEndTimeMachineSpinner()
        setUpReasonDropDown()

        setUpStartTimeMachineSpinner()
        setUpStartTimeReasonSpinner()

        endTimeEditText.isFocusable = false
        endTimeEditText.isClickable = true

        /*startTimeEditText.isFocusable = false
        startTimeEditText.isClickable = true*/
    }

    private fun setUpStartTimeReasonSpinner() {
        val dataString = ArrayList<String>()
        dataString.add("Reason 1")
        dataString.add("Reason 2")
        dataString.add("Reason 3")
        dataString.add("Reason 4")
        dataString.add("Reason 5")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        startTimeReasonSpinner.adapter = spinnerAdapter

        startTimeReasonSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        startTimeReasonSpinner.setSelection(0)
    }

    private fun setUpStartTimeMachineSpinner() {

        val dataString = ArrayList<String>()
        dataString.add("1")
        dataString.add("2")
        dataString.add("3")
        dataString.add("4")
        dataString.add("5")
        dataString.add("6")
        dataString.add("7")
        dataString.add("8")
        dataString.add("9")
        dataString.add("10")
        dataString.add("11")
        dataString.add("12")
        dataString.add("13")
        dataString.add("14")
        dataString.add("15")
        dataString.add("16")
        dataString.add("17")
        dataString.add("18")
        dataString.add("19")
        dataString.add("20")
        dataString.add("21")
        dataString.add("22")
        dataString.add("23")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        startTimeBeforeMachineSpinner.adapter = spinnerAdapter

        startTimeBeforeMachineSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        startTimeBeforeMachineSpinner.setSelection(0)
    }

    private fun setUpReasonDropDown() {
        val dataString = ArrayList<String>()
        dataString.add("Reason 1")
        dataString.add("Reason 2")
        dataString.add("Reason 3")
        dataString.add("Reason 4")
        dataString.add("Reason 5")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        reasonSpinner.adapter = spinnerAdapter

        reasonSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        reasonSpinner.setSelection(0)

    }

    private fun setUpEndTimeMachineSpinner() {

        val dataString = ArrayList<String>()
        dataString.add("1")
        dataString.add("2")
        dataString.add("3")
        dataString.add("4")
        dataString.add("5")
        dataString.add("6")
        dataString.add("7")
        dataString.add("8")
        dataString.add("9")
        dataString.add("10")
        dataString.add("11")
        dataString.add("12")
        dataString.add("13")
        dataString.add("14")
        dataString.add("15")
        dataString.add("16")
        dataString.add("17")
        dataString.add("18")
        dataString.add("19")
        dataString.add("20")
        dataString.add("21")
        dataString.add("22")
        dataString.add("23")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        endTimeMachineSpinner.adapter = spinnerAdapter

        endTimeMachineSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        endTimeMachineSpinner.setSelection(0)
    }

    private fun setUpWeightMachineSpinner() {

        val dataString = ArrayList<String>()
        dataString.add("1")
        dataString.add("2")
        dataString.add("3")
        dataString.add("4")
        dataString.add("5")
        dataString.add("6")
        dataString.add("7")
        dataString.add("8")
        dataString.add("9")
        dataString.add("10")
        dataString.add("11")
        dataString.add("12")
        dataString.add("13")
        dataString.add("14")
        dataString.add("15")
        dataString.add("16")
        dataString.add("17")
        dataString.add("18")
        dataString.add("19")
        dataString.add("20")
        dataString.add("21")
        dataString.add("22")
        dataString.add("23")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        weightMachineSpinner.adapter = spinnerAdapter

        weightMachineSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        weightMachineSpinner.setSelection(0)
    }

    private fun setUpBundleNumberSpinner() {
        val dataString = ArrayList<String>()
        dataString.add("1")
        dataString.add("2")
        dataString.add("3")
        dataString.add("4")
        dataString.add("5")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        bundleNumberSpinner.adapter = spinnerAdapter

        bundleNumberSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        bundleNumberSpinner.setSelection(0)

    }

    /*Set Up Nationality Spinner*/
    private fun setUpBundleTypeSpinner() {
        val dataString = ArrayList<String>()
        dataString.add("Manual")
        dataString.add("Bublin")
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        bundledTypeSpinner.adapter = spinnerAdapter

        bundledTypeSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        bundledTypeSpinner.setSelection(0)
    }


    @OnClick(R.id.endTimeEditText, R.id.startTimeEditText, R.id.btnPowerCut, R.id.btnShiftEnd)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.endTimeEditText -> {
                timeType = TIME_TYPE.END_TIME
                openTimePicker()
            }
            R.id.startTimeEditText -> {
                timeType = TIME_TYPE.START_TIME
                openTimePicker()
            }
            R.id.btnPowerCut -> {
                MaterialDialog.Builder(context!!)
                    .content("Is Power On?")
                    .negativeText(getString(R.string.no))
                    .positiveText(getString(R.string.yes))
                    .positiveColor(get_color(R.color.colorPrimary))
                    .negativeColor(get_color(R.color.colorBlack))
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        openFragment(ThirdFormFragment.newInstance())
                    }
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
            R.id.btnShiftEnd -> {
                MaterialDialog.Builder(context!!)
                    .content("Is Power On?")
                    .negativeText(getString(R.string.no))
                    .positiveText(getString(R.string.yes))
                    .positiveColor(get_color(R.color.colorPrimary))
                    .negativeColor(get_color(R.color.colorBlack))
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        openFragment(FourthForm1Fragment.newInstance())
                    }
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun openTimePicker() {

        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(
            context, this
            , hour, minute, true
        )//Yes 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }
}