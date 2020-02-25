package com.maxx.eparchi.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxx.eparchi.R
import com.maxx.eparchi.model.LaminationData
import com.maxx.eparchi.utils.kotlin.utils.extensions.hide
import com.maxx.eparchi.utils.kotlin.utils.extensions.show
import kotlinx.android.synthetic.main.adapter_parent.view.*


/**
 * Created by maxx on 6/19/17.
 */
class ParentAdapter(private val context: Context?, private val arrayList: List<LaminationData?>?) :
    RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_parent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionHistory = arrayList?.get(position)
        holder.bind(transactionHistory, position)
    }


    override fun getItemCount(): Int {
        return arrayList?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: LaminationData?, position: Int) {
            with(itemView) {
                dateTextView.text = data?.title.toString()
                dateTextView.setOnClickListener {
                    data?.showList = !data?.showList!!
                    if (data?.showList!!)
                        itemRecyclerView.show()
                    else
                        itemRecyclerView.hide()
                }
                if (data?.showList!!)
                    itemRecyclerView.show()
                else
                    itemRecyclerView.hide()
                itemRecyclerView.apply {
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = false
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    val recentTxnAdapter = ChildAdapter(context, data?.crucibleList)
                    adapter = recentTxnAdapter
                }
            }

        }
    }
}
