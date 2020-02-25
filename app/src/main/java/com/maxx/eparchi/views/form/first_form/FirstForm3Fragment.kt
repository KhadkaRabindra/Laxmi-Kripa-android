package com.maxx.eparchi.views.form.first_form

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.FirstFormData
import com.maxx.eparchi.views.adapters.FirstFormAdapter
import com.maxx.eparchi.views.form.second_form.SecondFormFragment
import com.maxx.eparchi.views.unused.home.HomeActivity
import kotlinx.android.synthetic.main.bottom_button_view.*

class FirstForm3Fragment : BaseFragment() {
    @BindView(R.id.recyclerView)
    lateinit var recyclerview: RecyclerView

    companion object {
        fun newInstance(): FirstForm3Fragment {
            return FirstForm3Fragment()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_first_fragment1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is HomeActivity)
            (activity as HomeActivity).setupToolbarTitle("Form 1")

        bottomButton.text = getString(R.string.submit)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
            setHasFixedSize(true)
            val recentTxnAdapter = FirstFormAdapter(activity, getData())
            adapter = recentTxnAdapter
        }
    }

    private fun getData(): List<FirstFormData>? {
        val arrayList = ArrayList<FirstFormData>()

        for (i in 17..23) {
            val data = FirstFormData()
            data.id = i.toString()
            arrayList.add(data)
        }
        return arrayList
    }

    @OnClick(R.id.bottomButton)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.bottomButton -> {
                openFragment(SecondFormFragment.newInstance())
            }
        }
    }
}