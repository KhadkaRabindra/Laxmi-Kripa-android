package com.maxx.eparchi.utils.kotlin.utils.extensions

import com.google.android.material.tabs.TabLayout


/**
 * Created by anup on 10/01/2019
 */

/**
 * return selected tab
 */
fun TabLayout.onTabSelected(onTabSelected: (TabLayout.Tab) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected.invoke(tab as TabLayout.Tab)
        }
    })
}