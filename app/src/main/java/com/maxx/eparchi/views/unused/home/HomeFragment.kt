package com.maxx.eparchi.views.unused.home

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.cardview.widget.CardView
import butterknife.BindView
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.listener.TimeListener
import com.maxx.eparchi.utils.view.TimePickerFragment
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), TimeListener {

    var amount = 20;

    @NotEmpty(sequence = 1, message = "Please enter weight.")
    @BindView(R.id.weightEditText)
    lateinit var etWeight: EditText

    @NotEmpty(sequence = 1, message = "Please enter time.")
    @BindView(R.id.timeEditText)
    lateinit var etTime: EditText


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTime.isClickable = false

        changeCardViewBg(twentyMainView)
        changeAmountTextColor(twentyTextView)
    }


    @OnClick(R.id.submitBtn, R.id.timeEditText, R.id.twentyMainView, R.id.twelveMainView, R.id.nineMainView)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.submitBtn -> {
                validate()
            }
            R.id.timeEditText ->{
                val newFragment = TimePickerFragment.newInstance(this)
                newFragment.show(fragmentManager!!, "TimePicker")
            }
            R.id.twentyMainView ->{
                changeCardViewBg(twentyMainView)
                changeAmountTextColor(twentyTextView)
                amount = 20
            }
            R.id.twelveMainView ->{
                changeCardViewBg(twelveMainView)
                changeAmountTextColor(twelveTextView)
                amount = 12
            }
            R.id.nineMainView ->{
                changeCardViewBg(nineMainView)
                changeAmountTextColor(nineTextView)
                amount = 9
            }
        }
    }

    fun changeCardViewBg(view: CardView?) {
        val cardViewArray = arrayOf<CardView>(twentyMainView, twelveMainView, nineMainView)
        for (cardView in cardViewArray)
            if (cardView == view)
                cardView.setCardBackgroundColor(get_color(R.color.color_blue_light))
            else
                cardView.setCardBackgroundColor(get_color(R.color.nav_selected_item_bg_color))
    }

    fun changeAmountTextColor(textView: TextView?) {
        val amountTextViewArray = arrayOf<TextView>(twentyTextView, twelveTextView, nineTextView)
        for (view in amountTextViewArray) {
            if (view == textView)
                view.setTextColor(get_color(R.color.colorWhite))
            else
                view.setTextColor(get_color(R.color.black_toolbar_title_color))
        }
    }

    override fun onValidationSucceeded() {
        super.onValidationSucceeded()

        /*if (internetAvailable())
            addReportPostData()
        else
            noNetworkConnectionDialog()*/
    }

    private fun addReportPostData() {
        /*show_progress_dialog()

        val addReportPostData = AddReportPostData(etWeight.text.toString(),
            etTime.text.toString(), amount.toString(), etTime.text.toString(), "1", "TEST")
        compositeDisposable.add(mApiService.addReport(addReportPostData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dissmiss_dialog()
                dialog(it.message.toString())
            }, {
                dissmiss_dialog()
                handleError(it)
            })
        )*/
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        etTime.setText("$hourOfDay:$minute")
    }


}