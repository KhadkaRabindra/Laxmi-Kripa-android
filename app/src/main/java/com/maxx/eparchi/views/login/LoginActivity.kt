package com.maxx.eparchi.views.login

import android.view.MenuItem
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar_base.*

class LoginActivity : BaseActivity() {
    override fun initView() {
        setupToolbar()

        openFragmentNoHistory(LoginFragment.newInstance())
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigate_before)
        }
    }

    fun setupToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showToolBar(){
        supportActionBar?.show()
    }

    fun hideToolBar(){
        supportActionBar?.hide()
    }
}