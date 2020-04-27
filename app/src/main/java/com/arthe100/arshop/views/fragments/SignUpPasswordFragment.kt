package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthInternalAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.views.BaseFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.sign_up_password_fragment.*
import javax.inject.Inject

class SignUpPasswordFragment : BaseFragment() {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager

    private lateinit var model: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(activity!! , viewModelProviderFactory).get(AuthViewModel::class.java)

        model.currentViewState.observe(this , Observer(::render))
    }

    private fun render(state: AuthState){
        when(state){
            is AuthState.Failure -> {
                messageManager.toast(activity!! , state.err.toString())
                loading_bar.visibility = View.INVISIBLE
            }
            is AuthState.SingupSuccess -> {
                model.onEvent(AuthUiAction
                    .LoginAction(
                        password = signup_password.text.toString(),
                        phone = model.phone))
            }
            is AuthState.LoginSuccess -> {
                loading_bar.visibility = View.INVISIBLE

                val pref = PreferenceManager.getDefaultSharedPreferences(context!!)
                with(pref.edit()){
                    putString("userData" , Gson().toJson(state.user))
                    commit()
                }

                messageManager.toast(activity!! , "Welcome!")
            }
            is AuthState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE
            }
        }
    }

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
            .inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.bottom_navbar.visibility = View.INVISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up_password_fragment, container, false)
    }


    override fun onStart() {
        setPasswordEditText()
        setRepeatPasswordEditText()

        confirm_password_btn.setOnClickListener {
            model.onEvent(AuthUiAction
                .SignupAction(
                    password = signup_password.text.toString(),
                    phone = model.phone))
        }

        super.onStart()
    }

    private fun setRepeatPasswordEditText() {
        var passwordVisible = false

        repeat_visibility_icon.setOnClickListener {
            if (passwordVisible) {
                signup_password_repeat.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                repeat_visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
            else {
                signup_password_repeat.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                repeat_visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }

    }

    private fun setPasswordEditText() {
        var passwordVisible = false

        signup_visibility_icon.setOnClickListener {
            if (passwordVisible) {
                signup_password.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                signup_visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
            else {
                signup_password.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                signup_visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }
    }

    override fun toString(): String {
        return "SignUpPassword"
    }

}
