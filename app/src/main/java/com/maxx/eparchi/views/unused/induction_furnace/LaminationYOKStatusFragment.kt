package com.maxx.eparchi.views.unused.induction_furnace

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.Crucible
import com.maxx.eparchi.model.LaminationData
import com.maxx.eparchi.model.Reason
import com.maxx.eparchi.views.adapters.ParentAdapter

class LaminationYOKStatusFragment : BaseFragment() {


    @BindView(R.id.recyclerView)
    lateinit var recyclerview: RecyclerView

    val reasonsList = arrayListOf<String>("Puncture State ", "Bugling", "Others")

    companion object{
        fun newInstance() : LaminationYOKStatusFragment {
            return LaminationYOKStatusFragment()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_lamination_yok_status

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUpExpandableListView(getData())

        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            val recentTxnAdapter = ParentAdapter(activity, getData())
            adapter = recentTxnAdapter
        }
    }

    /*Set up expandable List view*/
    /*private fun setUpExpandableListView(arrtemp_Records:  ArrayList<LaminationData>) {
        adapter = TransactionHistoryAdapter2(activity?.applicationContext!!, arrtemp_Records!!)
        expandableList.setAdapter(adapter)
    }*/

    fun getData() : ArrayList<LaminationData>{
        val laminationList = ArrayList<LaminationData>()

        for (i in 1..8){
            val laminationData = LaminationData()
            laminationData.title = "Crucible $i"
            laminationData.showList = false

            val crucibleList = ArrayList<Crucible>()
            for ( i in 1..14){
                val crucible = Crucible()
                crucible.id = i.toString()
                crucible.isOK = i % 2 == 0
                val reasonList = ArrayList<Reason>()
                for (j in reasonsList.indices){
                    val reason = Reason()
                    reason.name = reasonsList[j]
                    reasonList.add(reason)
                }
                crucible.reasonList = reasonList
                crucibleList.add(crucible)
            }
            laminationData.crucibleList = crucibleList

            laminationList.add(laminationData)
        }
        return laminationList

    }
}