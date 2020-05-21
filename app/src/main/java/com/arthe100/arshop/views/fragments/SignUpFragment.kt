package com.arthe100.arshop.views.fragments

import android.os.Bundle
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
import kotlinx.android.synthetic.main.sign_up_fragment.*
import javax.inject.Inject


class SignUpFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession,
    private val dialogBox: DialogBoxManager
) : BaseFragment() {


    private lateinit var model: AuthViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
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
        return "SignUp"
    }


    private fun render(state: AuthState){
        when(state){
            is AuthState.Failure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.ERROR)
            }

            is AuthState.SingupSuccess -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                loadFragment(VerifyFragment::class.java)
            }

            is AuthState.LoadingState -> {
                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
        }
    }
}
