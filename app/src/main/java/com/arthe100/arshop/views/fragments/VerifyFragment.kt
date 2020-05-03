package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.sign_up_password_fragment.*
import kotlinx.android.synthetic.main.verify_fragment_layout.*
import kotlinx.android.synthetic.main.verify_fragment_layout.loading_bar
import javax.inject.Inject
import kotlin.math.log

class VerifyFragment : BaseFragment(){

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager
    lateinit var signUpPasswordFragment: SignUpPasswordFragment

    private lateinit var model: AuthViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpPasswordFragment = FragmentFactory.create(FragmentType.SIGNUP_PASSWORD)
                as SignUpPasswordFragment
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model.currentViewState.observe(this , Observer(::render))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.verify_fragment_layout, container, false)
    }

    override fun onStart() {

        verify_continue_btn.setOnClickListener {
            model.onEvent(AuthUiAction.CheckCodeAction(code_input.text.toString()))
        }
        super.onStart()
    }

    override fun toString(): String {
        return "Verify Fragment"
    }

    private fun render(state: AuthState){
        when(state){
            is AuthState.Failure -> {
                loading_bar.visibility = View.INVISIBLE
                messageManager.toast(requireActivity() , state.err.toString())
            }
            is AuthState.CodeSuccess -> {
                loading_bar.visibility = View.INVISIBLE
                loadFragment(signUpPasswordFragment)
            }
            is AuthState.LoadingState ->{
                loading_bar.visibility = View.VISIBLE
            }
        }
    }

}
