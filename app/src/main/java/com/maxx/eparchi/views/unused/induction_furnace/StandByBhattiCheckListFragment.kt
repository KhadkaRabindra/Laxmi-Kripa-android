package com.maxx.eparchi.views.unused.induction_furnace

import android.view.View
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment

class StandByBhattiCheckListFragment : BaseFragment() {

    companion object{
        fun newInstance() : StandByBhattiCheckListFragment {
            val fragment = StandByBhattiCheckListFragment()
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_stand_by_bhatti


    @OnClick(R.id.nextBtn)
    fun onViewClicked(view : View){
        when(view.id){
            R.id.nextBtn ->{
                openFragment(LaminationYOKStatusFragment.newInstance())
            }
        }
    }
}