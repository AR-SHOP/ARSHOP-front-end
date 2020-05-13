package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.verify_fragment_layout.*
import kotlinx.android.synthetic.main.verify_fragment_layout.loading_bar
import javax.inject.Inject

class VerifyFragment : BaseFragment(){

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession
    @Inject lateinit var messageManager: MessageManager

    private lateinit var profileFragment: ProfileFragment
    private lateinit var model: AuthViewModel


    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileFragment = fragmentFactory.create<ProfileFragment>()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model.currentViewState.observe(this , Observer(::render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.verify_fragment_layout, container, false)
    }

    override fun onStart() {

        verify_btn.setOnClickListener {
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
                DialogBoxManager.showDialog(requireActivity(), MessageType.ERROR)
            }

            is AuthState.CodeSuccess -> {
                model.onEvent(AuthUiAction.LoginAction(model.password , model.phone))
            }

            is AuthState.LoginSuccess -> {
                DialogBoxManager.cancel()
                session.saveUser(state.user)
                loadFragment(profileFragment)
            }

            is AuthState.LoadingState ->{
                DialogBoxManager.showDialog(requireActivity(), MessageType.LOAD)
            }
        }
    }

}
