package com.maxx.eparchi.views.unused.home

import android.view.MenuItem
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseActivity
import com.maxx.eparchi.views.form.first_form.FirstForm1Fragment
import kotlinx.android.synthetic.main.toolbar_base.*

class HomeActivity : BaseActivity() {
    override fun initView() {
        openFragmentNoHistory(FirstForm1Fragment.newInstance())
        setupToolbar()
        setupToolbarTitle("Form 1")
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_main

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

}
