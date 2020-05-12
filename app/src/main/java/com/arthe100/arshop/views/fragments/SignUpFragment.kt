package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.phone_number_fragment_layout.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession

    private lateinit var verifyFragment: VerifyFragment
    private lateinit var model: AuthViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyFragment = fragmentFactory.create<VerifyFragment>()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model.currentViewState.observe(this , Observer(::render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()

        signup_btn.setOnClickListener {
            model.phone = signup_username.text.toString()
            model.onEvent(AuthUiAction
                .SignupAction(
                    password = signup_password.text.toString(),
                    phone = model.phone))
        }

    }


    override fun toString(): String {
        return "SignUp Fragment"
    }


    private fun render(state: AuthState){
        when(state){
            is AuthState.Failure -> {
//                DialogBoxManager.create(activity, MessageType.ERROR, state.err.toString())
            }

            is AuthState.SingupSuccess -> {
                model.onEvent(
                    AuthUiAction
                    .LoginAction(
                        password = signup_password.text.toString(),
                        phone = model.phone))
            }

            is AuthState.LoginSuccess -> {
                session.saveUser(state.user)
//                DialogBoxManager.create(activity, MessageType.SUCCESS)
                loadFragment(verifyFragment)
            }

            is AuthState.LoadingState -> {
//                DialogBoxManager.create(activity, MessageType.LOAD)
            }
        }
    }


}
