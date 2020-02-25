package com.maxx.eparchi.views.form.fourth_form

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.FourthFormData
import com.maxx.eparchi.views.adapters.FourthFormAdapter
import com.maxx.eparchi.views.unused.home.HomeActivity
import kotlinx.android.synthetic.main.bottom_button_view.*

class FourthForm2Fragment : BaseFragment() {
    @BindView(R.id.recyclerView)
    lateinit var recyclerview: RecyclerView

    companion object {
        fun newInstance(): FourthForm2Fragment {
            val fragment = FourthForm2Fragment()
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_form_four

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is HomeActivity)
            (activity as HomeActivity).setupToolbarTitle("Form 4")


        bottomButton.text = getString(R.string.submit)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
            setHasFixedSize(true)
            val recentTxnAdapter = FourthFormAdapter(activity, getData())
            adapter = recentTxnAdapter
        }
    }

    private fun getData(): List<FourthFormData>? {
        val arrayList = ArrayList<FourthFormData>()

        for (i in 9..16) {
            val data = FourthFormData()
            data.id = i.toString()
            arrayList.add(data)
        }
        return arrayList
    }

    @OnClick(R.id.bottomButton)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.bottomButton -> {
                openFragment(FourthForm3Fragment.newInstance())
            }
        }
    }

}