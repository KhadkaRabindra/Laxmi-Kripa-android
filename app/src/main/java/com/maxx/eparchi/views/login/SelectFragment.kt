package com.maxx.eparchi.views.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.LoginResponse
import com.maxx.eparchi.views.unused.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_select.*

class SelectFragment : BaseFragment() {
    var loginResponse :LoginResponse? = null

    companion object {
        fun newInstance(it : LoginResponse): SelectFragment {
            val fragment = SelectFragment()
            fragment.loginResponse = it
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_select


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity is LoginActivity){
            (activity as LoginActivity).setupToolbarTitle("Select")
            (activity as LoginActivity).showToolBar()
        }


        setUpUserNameSpinner()
        //setUpPostSpinner()
    }

    private fun setUpPostSpinner() {
        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val dataString = ArrayList<String>()

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        postTypeSpinner.adapter = spinnerAdapter

        postTypeSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        postTypeSpinner.setSelection(0)
    }

    /*Set Up Nationality Spinner*/
    private fun setUpUserNameSpinner() {
        val dataString = ArrayList<String>()
        val natureOfBuisnessList = loginResponse?.users
        natureOfBuisnessList?.mapTo(dataString) { it?.fields?.uname.toString() }

        /*var nationalityList = response?.nationalityList as ArrayList<NationalityList>
        nationalityList.mapTo(dataString) { it.text.toString() }*/

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        userNameSpinner.adapter = spinnerAdapter

        userNameSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*mSelectedNationality = nationalityList?.get(position)*/
            }
        })
        userNameSpinner.setSelection(0)
    }

    @OnClick(R.id.btnNext)
    fun onViewClikced(view: View) {
        when (view.id) {
            R.id.btnNext -> {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

}