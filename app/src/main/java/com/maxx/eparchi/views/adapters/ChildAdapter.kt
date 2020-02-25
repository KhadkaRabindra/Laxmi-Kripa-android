package com.maxx.eparchi.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.maxx.eparchi.R
import com.maxx.eparchi.model.Crucible
import com.maxx.eparchi.utils.kotlin.utils.extensions.invisible
import com.maxx.eparchi.utils.kotlin.utils.extensions.show
import com.maxx.eparchi.utils.view.NoDefaultARSpinner2


class ChildAdapter(private val context: Context?, private val arrayList: List<Crucible>?) :
    RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_child, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crucible = arrayList?.get(position)

        holder.idTextView.text = crucible?.id ?: ""
        /*if (transaction?.isOK!!)
            holder.isOkTextView.text = "OK"
        else
            holder.isOkTextView.text = "Is not OK"*/
        if (crucible?.isOK!!) {
            holder.radioGroup.check(R.id.radioOk)
        } else {
            holder.radioGroup.check(R.id.radioNotOk)
        }

        if (crucible.isOK!!) {
            holder.spinner.invisible()
        } else {
            holder.spinner.show()

            setUpSpinner(holder, crucible)
        }

        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioOk) {
                crucible.isOK = true
                holder.spinner.invisible()
            } else if (checkedId == R.id.radioNotOk) {
                crucible.isOK = false
                holder.spinner.show()
                setUpSpinner(holder, crucible)
            }
        }
    }

    private fun setUpSpinner(holder: ViewHolder, transaction : Crucible) {

        val dataString = ArrayList<String>()
        val natureOfBuisnessList = transaction?.reasonList
        natureOfBuisnessList?.mapTo(dataString) { it.name.toString() }

        val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_selected_row, dataString)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_row)
        holder.spinner.adapter = spinnerAdapter
        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, selectedPosition: Int, id: Long) {
                transaction.selection = selectedPosition
            }
        }

        if (transaction.selection != null) {
            holder.spinner.setSelection(transaction.selection!!)
        }
    }

    override fun getItemCount(): Int {
        return arrayList?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.idTextView)
        lateinit var idTextView: TextView
        @BindView(R.id.spinner)
        lateinit var spinner: NoDefaultARSpinner2

        @BindView(R.id.radio)
        lateinit var radioGroup: RadioGroup
        @BindView(R.id.radioOk)
        lateinit var radioButtonOk: RadioButton
        @BindView(R.id.radioNotOk)
        lateinit var radioButtonNotOK: RadioButton

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
