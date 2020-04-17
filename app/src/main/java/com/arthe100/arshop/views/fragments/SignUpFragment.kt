package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.mviBase.MviView
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.signup_fragment_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import javax.inject.Inject

class SignUpFragment : BaseFragment(){

    private val TAG = SignUpFragment::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    lateinit var authViewModel: AuthViewModel


    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()

        authViewModel = ViewModelProvider(this, viewModelProviderFactory).get(AuthViewModel::class.java)

        authViewModel.currentViewState.observe(this , Observer { render(it) })


        btnSignUp.setOnClickListener{

            val isValid =
                password.text.trim().toString() == repeat_password.text.trim().toString()
            if(!isValid){
                messageManager.toast(activity!! , "passwords doesn't match")
                return@setOnClickListener
            }

            authViewModel.onEvent(AuthUiAction.SignupAction(
                username = username.text.trim().toString(),
                email = username.text.trim().toString(),
                password = password.text.trim().toString()
            ))
        }
    }

    private fun render(state : AuthState){
        when(state)
        {
            is AuthState.Idle -> messageManager.toast(activity!! , "Idle...")
            is AuthState.LoadingState -> messageManager.toast(activity!! , "loading...")
            is AuthState.Success -> messageManager.toast(activity!! , state.user.toString())
            is AuthState.Failure -> messageManager.toast(activity!! , state.err.toString())
        }
    }

}
