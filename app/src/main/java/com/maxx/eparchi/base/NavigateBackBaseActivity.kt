package com.maxx.eparchi.base

import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.model.ViewType
import com.maxx.eparchi.utils.Constants
import kotlinx.android.synthetic.main.toolbar_base.*

class NavigateBackBaseActivity : BaseActivity() {

    @BindView(R.id.notificationImageView)
    lateinit var ivNotification: ImageView
    @BindView(R.id.shareImageView)
    lateinit var ivShare: ImageView

    override val layoutResourceId: Int
        get() = R.layout.activity_base

    override fun initView() {
        setupToolbar()
        val viewType = intent?.extras?.getSerializable(Constants.VIEW_TYPE_INTENT) as? ViewType
        setFragment(viewType)
    }

    private fun setFragment(viewType: ViewType?) {
        when (viewType) {
            ViewType.APPLY_LEAVE -> {
            }
            ViewType.APPLY_TRAVEL_LEAVE ->{
            }
            ViewType.PROFILE ->{
            }
            ViewType.ATTENDANCE ->{
            }
            ViewType.CHANGE_PASSWORD ->{
            }
        }
    }

    fun setupToolbar() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigate_before)
        }
    }

    fun setupToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    @OnClick(R.id.notificationImageView)
    fun showHideCalendar() {
        //listener.onCalendarClicked();
        /*if (supportFragmentManager.findFragmentById(R.id.container) is TransactionFragment) {
            val fragmentB = supportFragmentManager.findFragmentById(R.id.container) as TransactionFragment
            fragmentB.showHideCalendar()
        }*/

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}