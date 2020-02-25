package com.maxx.eparchi.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.afollestad.materialdialogs.MaterialDialog
import com.maxx.eparchi.MyApplication
import com.maxx.eparchi.R
import com.maxx.eparchi.utils.Constants
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.maxx.eparchi.utils.kotlin.utils.extensions.errorToast
import com.maxx.eparchi.utils.kotlin.utils.extensions.parseError
import com.maxx.eparchi.utils.view.CustomProgressBarDialog
import com.maxx.eparchi.utils.retrofit.ApiService
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

abstract class BaseFragment : Fragment(), Validator.ValidationListener {
    protected var mValidator: Validator? = null
    lateinit var mApiService: ApiService
    protected var imm: InputMethodManager? = null

    protected var compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeValidator()
        mApiService = (activity!!.application as MyApplication).getAPIService()
    }

    private fun initializeValidator() {
        mValidator = Validator(this)
        mValidator!!.setValidationListener(this)
    }

    fun validate() {
        initializeValidator()
        mValidator!!.validate()
    }

    abstract val layoutId: Int


    fun openFragment(fragment: Fragment, tag: String = Constants.DEFAULT_TAG) {
        (activity as BaseActivity).openFragment(fragment, tag = tag)
    }

    fun addFragment(fragment: Fragment) {
        (activity as BaseActivity).addFragment(fragment)
    }

    fun openFragmentNoHistory(fragment: Fragment) {
        (activity as BaseActivity).openFragmentNoHistory(fragment)
    }

    fun show_progress_dialog() {
        if (activity != null && isAdded) {
            CustomProgressBarDialog.progressDialog = CustomProgressBarDialog.showProgressDialog(activity)
            CustomProgressBarDialog.progressDialog.show()
        }
    }

    fun dissmiss_dialog() {
        if (CustomProgressBarDialog.progressDialog != null && CustomProgressBarDialog.progressDialog.isShowing)
            CustomProgressBarDialog.progressDialog.dismiss()
    }


    fun internetAvailable(): Boolean {
        return (context as BaseActivity).internetAvailable()
    }

    fun fromHtml(str: String): Spanned {
        return (activity as BaseActivity).fromHtml(str)
    }

    override fun onValidationSucceeded() {

    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view = error.view
            val message = error.getCollatedErrorMessage(activity)
            // Display error messages ;)
            if (view is EditText)
                view.error = message
            else
                errorToast(message)

        }
    }

    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!userVisibleHint) {
            return
        }

        //INSERT CUSTOM CODE HERE
        onFragmentVisible()
    }

    fun onFragmentVisible() {

    }


    fun get_color(colorCode: Int): Int {
        return ContextCompat.getColor(context!!, colorCode)
    }

    fun dialog(message: String) {
        MaterialDialog.Builder(context!!)
            .content(message)
            .positiveText(getString(R.string.ok))
            .onPositive { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun noNetworkConnectionDialog() {
        dialog(getString(R.string.no_internet_connection_message))
    }


    fun hideKeyBoard(editText: EditText?) {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(editText?.windowToken, 0)//hide keyboard
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
        dissmiss_dialog()
    }

    fun onBackPressed(): Boolean {
        return false
    }

    protected fun handleError(t: Throwable) {
        if (t is HttpException) {
            val code = (t as HttpException).response().code()
            if (code != Constants.APP_FORCE_UPDATE_ERROR_CODE)
                dialog(parseError(t).toString())
        } else
            dialog(parseError(t).toString())
    }

    override fun onPause() {
        super.onPause()
        dissmiss_dialog()
    }

    fun onViewFocusedTrue(view: CardView?) {
        view?.setCardBackgroundColor(get_color(R.color.colorWhite))
        if (view != null)
            view.cardElevation = 12f
    }

    fun onViewFocusedFalse(view: CardView?) {
        view?.setCardBackgroundColor(get_color(R.color.nav_selected_item_bg_color))
        if (view != null)
            view.cardElevation = 0f
    }

}