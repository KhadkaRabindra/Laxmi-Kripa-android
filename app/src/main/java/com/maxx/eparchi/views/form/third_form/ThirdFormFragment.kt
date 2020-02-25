package com.maxx.eparchi.views.form.third_form

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.utils.kotlin.utils.extensions.toast
import com.maxx.eparchi.views.form.fifth_form.FifthFormFragment
import com.maxx.eparchi.views.form.fourth_form.FourthForm1Fragment
import com.maxx.eparchi.views.form.second_form.SecondFormFragment
import com.maxx.eparchi.views.unused.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_third_form.*
import kotlinx.android.synthetic.main.second_form_start_first_time.*
import kotlinx.android.synthetic.main.second_form_start_first_time.btnShiftEnd
import kotlinx.android.synthetic.main.second_form_weight.*
import kotlinx.android.synthetic.main.second_form_weight.bundleNumberSpinner
import kotlinx.android.synthetic.main.second_form_weight.bundledTypeSpinner
import kotlinx.android.synthetic.main.second_form_weight.weightMachineSpinner

class ThirdFormFragment : BaseFragment() {

    companion object {
        fun newInstance(): ThirdFormFragment {
            return ThirdFormFragment()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_third_form

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is HomeActivity)
            (activity as HomeActivity).setupToolbarTitle("Form 3")

        setUpWeightMachineSpinner()
        setUpBundleTypeSpinner()
        setUpBundleNumberSpinner()
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


    @OnClick(R.id.btnPowerOn, R.id.btnShiftEnd)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.btnPowerOn -> {
                MaterialDialog.Builder(context!!)
                    .content( "Is Power On?")
                    .negativeText(getString(R.string.no))
                    .positiveText(getString(R.string.yes))
                    .positiveColor(get_color(R.color.colorPrimary))
                    .negativeColor(get_color(R.color.colorBlack))
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        openFragment(SecondFormFragment.newInstance())
                    }
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
            R.id.btnShiftEnd -> {

                MaterialDialog.Builder(context!!)
                    .content( "Is Shift End?")
                    .negativeText(getString(R.string.no))
                    .positiveText(getString(R.string.yes))
                    .positiveColor(get_color(R.color.colorPrimary))
                    .negativeColor(get_color(R.color.colorBlack))
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        openFragment(FifthFormFragment.newInstance())
                    }
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}