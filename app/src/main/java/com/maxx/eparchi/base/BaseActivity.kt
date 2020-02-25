package com.maxx.eparchi.base


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import butterknife.ButterKnife
import com.afollestad.materialdialogs.MaterialDialog
import com.maxx.eparchi.MyApplication
import com.maxx.eparchi.R
import com.maxx.eparchi.utils.Connectivity
import com.maxx.eparchi.utils.Constants
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.maxx.eparchi.utils.kotlin.utils.extensions.infoToast
import com.maxx.eparchi.utils.kotlin.utils.extensions.parseError
import com.maxx.eparchi.utils.view.CustomProgressBarDialog
import com.maxx.eparchi.utils.CommonUtils
import com.maxx.eparchi.utils.view.Alerts
import com.maxx.eparchi.utils.retrofit.ApiService
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException


abstract class BaseActivity : AppCompatActivity(), Validator.ValidationListener {

    protected var mContext: Context? = null
    protected var mValidator: Validator? = null
    protected var mBundle: Bundle? = null
    protected var mProgressDialog: ProgressDialog? = null

    //for localization
    var languageToLoad = "en" // default english
    var prevLanguageCode: String? = null
    lateinit var res: Resources
    lateinit var dm: DisplayMetrics
    lateinit var conf: Configuration
    lateinit var mApiService: ApiService

    protected var updateAlertDialog: AlertDialog? = null
    //protected var mVersion: Version? = null

    protected var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOncreate()
        //CommonUtils.setUpKeyValueForCrashLog()
    }

    protected abstract val layoutResourceId: Int

    protected abstract fun initView()


    private fun initOncreate() {
        setContentView(layoutResourceId)
        ButterKnife.bind(this)

        //enableStrictMode()

        mBundle = intent.extras
        mContext = this

        mApiService = (application as MyApplication).getAPIService()
        //initialize saripaar validation
        initializeValidator()
        //initialize view
        initView()
        compositeDisposable = CompositeDisposable()
    }


    private fun initializeValidator() {
        mValidator = Validator(this)
        mValidator!!.setValidationListener(this)
    }

    fun validate() {
        initializeValidator()
        mValidator!!.validate()
    }

    fun openFragment(
        fragment: Fragment,
        cleanStack: Boolean = false,
        addToBackStack: Boolean = true,
        tag: String = Constants.DEFAULT_TAG
    ) {
        val ft = supportFragmentManager
            .beginTransaction()
        if (cleanStack)
            clearBackStack()
        ft.replace(
            R.id.container,
            fragment, tag
        )
        if (addToBackStack)
            ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commitAllowingStateLoss()
    }

    fun addFragment(
        fragment: Fragment,
        cleanStack: Boolean = false,
        addToBackStack: Boolean = true,
        tag: String = Constants.DEFAULT_TAG
    ) {
        val ft = supportFragmentManager
            .beginTransaction()
        if (cleanStack)
            clearBackStack()
        ft.add(
            R.id.container,
            fragment
        )
        if (addToBackStack)
            ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commitAllowingStateLoss()
    }

    fun clearChildBackStack() {
        if (!isFinishing) {
            val manager = supportFragmentManager
            if (manager.backStackEntryCount > 0) {
                val first = manager.getBackStackEntryAt(0)
                manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    fun clearBackStack() {
        if (!isFinishing) {
            val manager = supportFragmentManager
            if (manager.backStackEntryCount > 0) {
                val first = manager.getBackStackEntryAt(0)
                manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    fun openFragmentNoHistory(fragment: Fragment) {
        openFragment(fragment, cleanStack = true, addToBackStack = false)
    }

    fun makeActivityFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun fromHtml(str: String): Spanned {
        return CommonUtils.fromHtml(str)
    }

    fun get_color(colorCode: Int): Int {
        return ContextCompat.getColor(mContext!!, colorCode)
    }


    override fun onValidationSucceeded() {

    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view = error.view
            val message = error.getCollatedErrorMessage(this)
            // Display error messages ;)
            if (view is EditText)
                view.error = message
            else
                infoToast(message)
        }
    }


    fun internetAvailable(): Boolean {
        return Connectivity.isConnected(mContext)
    }


    fun checkIntentKey(KEY: String): Boolean {
        if (intent != null && intent.extras != null && intent.extras.containsKey(KEY))
            return true
        return false
    }

    protected fun addBackButtonInToolbar(toolbar: Toolbar) {
        try {
            //change back arrow color
            val upArrow = ContextCompat.getDrawable(mContext!!, R.drawable.abc_ic_ab_back_material)
            upArrow?.setColorFilter(get_color(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)
            supportActionBar!!.setHomeAsUpIndicator(upArrow)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //set back arrow click listener
        toolbar.setNavigationOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
            else
                finish()
        }
    }

    override fun onResume() {
        super.onResume()
        Alerts.register(this)
    }


    override fun onPause() {
        super.onPause()
        Alerts.unregister(this)
        dissmiss_dialog()
    }

    companion object {

        private val TAG = BaseActivity::class.java.simpleName
    }

    /**
     * google icon drawable
     */
    fun getGoogleIconDrawable(name: GoogleMaterial.Icon, color: Int, size: Int): Drawable {
        return IconicsDrawable(mContext)
            .icon(name)
            .color(ContextCompat.getColor(mContext!!, color))
            .sizeDp(size)
    }

    fun show_progress_dialog() {
        if (applicationContext != null && !isDestroyed) {
            CustomProgressBarDialog.progressDialog.show()
        }

    }

    fun dissmiss_dialog() {
        if (CustomProgressBarDialog.progressDialog != null && CustomProgressBarDialog.progressDialog.isShowing)
            CustomProgressBarDialog.progressDialog.dismiss()
    }

    fun noNetworkConnectionDialog() {
        dialog(getString(R.string.no_internet_connection_message))
    }

    fun dialog(message: String) {
        if (applicationContext != null && !isDestroyed) {
            MaterialDialog.Builder(this)
                .content(message)
                .positiveText(getString(R.string.ok))
                .onPositive { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

    }

    protected fun handleError(t: Throwable) {
        if (t is HttpException) {
            val code = (t as HttpException).response().code()
            if (code != Constants.APP_FORCE_UPDATE_ERROR_CODE)
                dialog(parseError(t).toString())
        } else
            dialog(parseError(t).toString())
    }


    fun handleErrorWithBlackButton(t: Throwable) {
        if (t is HttpException) {
            val code = (t as HttpException).response().code()
            if (code != Constants.APP_FORCE_UPDATE_ERROR_CODE)
                showButtonDialog(parseError(t).toString())
        } else
            showButtonDialog(parseError(t).toString())
    }

    private fun showButtonDialog(msg: String) {
        if (applicationContext != null && !isDestroyed) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setMessage(msg)
            builder.setPositiveButton(
                "OK"
            ) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        dissmiss_dialog()
        if (updateAlertDialog != null && updateAlertDialog?.isShowing!!)
            updateAlertDialog!!.dismiss()
        compositeDisposable?.clear()
    }

    protected fun fixToolbarHeight(view: View) {
        if (Build.VERSION.SDK_INT >= 21) {
            // Set the status bar to dark-semi-transparentish
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            view.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * get image title
     *
     */
    fun getImageTitle(): String {
        return String.format("mta_profile_%d.jpg", System.currentTimeMillis())
    }


    /**
     * Check if the app level notification is enabled or not
     */
    fun isAppNotificationEnabled(): Boolean {
        return NotificationManagerCompat.from(this).areNotificationsEnabled()
    }

    /**
     * launch app notification setting page
     */

    fun redirectToAppNotification() {
        intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // for Android O
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

        } else {
            //for Android 5-7
            intent.putExtra("app_package", packageName)
            intent.putExtra("app_uid", applicationInfo.uid)
        }
        startActivity(intent)
    }
}
