package com.maxx.eparchi.views.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.maxx.eparchi.R
import com.maxx.eparchi.base.BaseFragment
import com.maxx.eparchi.model.LoginPostData
import com.maxx.eparchi.model.LoginResponse
import com.maxx.eparchi.utils.kotlin.utils.Util
import com.maxx.eparchi.utils.kotlin.utils.extensions.textValue
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginFragment : BaseFragment() {

    @NotEmpty
    @BindView(R.id.userNameEditText)
    lateinit var userNameEditText: EditText
    @NotEmpty
    @BindView(R.id.passwordEditText)
    lateinit var passwordEditText: EditText

    companion object {
        fun newInstance(): LoginFragment {
            val fragment = LoginFragment()
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    @OnClick(R.id.loginButton)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                validate()
            }
        }
    }

    override fun onValidationSucceeded() {
        super.onValidationSucceeded()

        if (internetAvailable())
            doLogin()
        else
            noNetworkConnectionDialog()
    }

    private fun doLogin() {
        show_progress_dialog()
        val username = userNameEditText.textValue
        val password = passwordEditText.textValue

        compositeDisposable.add(
            mApiService.login(LoginPostData(username = username, password = password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dissmiss_dialog()
                    handleLoginSuccess(it)
                }, {
                    dissmiss_dialog()
                    handleError(it)
                })
        )

    }

    private fun handleLoginSuccess(it : LoginResponse) {
        Util.saveUserInfoFromResponse(it)
        openFragment(SelectFragment.newInstance(it))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is LoginActivity) {
            (activity as LoginActivity).setupToolbarTitle("Login")
            (activity as LoginActivity).hideToolBar()
        }

    }

}