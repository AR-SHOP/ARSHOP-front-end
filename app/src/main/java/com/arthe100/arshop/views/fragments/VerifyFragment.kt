package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.base.AuthState
import com.arthe100.arshop.scripts.mvi.base.AuthUiAction
import com.arthe100.arshop.scripts.mvi.base.CartUiAction
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.verify_fragment_layout.*
import javax.inject.Inject

class VerifyFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession,
    private val messageManager: MessageManager,
    private val dialogBox: DialogBoxManager
) : BaseFragment(){

    private lateinit var model: AuthViewModel
    private lateinit var cartViewModel: CartViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        return inflater.inflate(R.layout.verify_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        model.onEvent(AuthUiAction.GetCodeAction(model.phone))
        verify_btn?.setOnClickListener {
            model.onEvent(AuthUiAction.CheckCodeAction(code_input.text.toString()))
        }
        send_again_btn?.setOnClickListener {
            model.onEvent(AuthUiAction.GetCodeAction(model.phone))
        }
        super.onStart()
    }

    override fun toString(): String {
        return "Verify Fragment"
    }

    override fun render(state: ViewState){
        when(state){
            is ViewState.Failure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.ERROR)
            }

            is AuthState.CodeGetSuccess -> {
                dialogBox.cancel()
                val reg = "\\d+".toRegex()
                model.code = reg.find(state.code , 0)?.value
            }
            is AuthState.CodeSuccess -> {
                model.onEvent(AuthUiAction.LoginAction(model.password , model.phone))
            }

            is AuthState.LoginSuccess -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                session.saveUser(state.user)
                cartViewModel.onEvent(CartUiAction.GetCart)
                loadFragment(ProfileFragment::class.java)
            }

            is ViewState.LoadingState ->{
//                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
        }
    }

}
