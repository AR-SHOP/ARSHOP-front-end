package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.interfaces.ILoadFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.login_fragment_layout.*
import javax.inject.Inject

class LoginFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession,
    private val dialogBox: DialogBoxManager
) : BaseFragment(), ILoadFragment {


    private lateinit var messageManager: MessageManager
    private lateinit var signUpFragment: SignUpFragment
    private lateinit var profileFragment: ProfileFragment
    private val TAG = LoginFragment::class.simpleName
    private lateinit var model: AuthViewModel
    private lateinit var cartViewModel: CartViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        messageManager = MessageManager()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()
        login_new_account.setOnClickListener{

            val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val user = pref.getString("userData" , null)

            if(user == null)
                loadFragment(SignUpFragment::class.java)
            else {
                messageManager.toast(requireContext(),
                    "already logged in! user: ${Gson().fromJson(user , User.User::class.java).username}")
            }
        }

        login_username.setText(model.phone)
        login_password.setText(model.password)

        login_username.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.phone = p0.toString()
            }
        })
        login_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.password = p0.toString()
            }
        })


        login_btn.setOnClickListener {
            model.onEvent(AuthUiAction.LoginAction(login_password.text.toString() , login_username.text.toString()))
        }

        login_username.apply {
            error = null //you can set any error for username
        }

        login_password.apply {
            error = null //you can set any error for the password
        }

    }

    override fun toString(): String {
        return "Login"
    }

    private fun render(state: AuthState){
        when(state){
            is AuthState.Idle -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
            }
            is AuthState.LoadingState -> {
//                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
            is AuthState.LoginSuccess -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                session.saveUser(state.user)
                cartViewModel.onEvent(CartUiAction.GetCart)
                loadFragment(ProfileFragment::class.java)
            }
            is AuthState.Failure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR)
            }
        }
    }
}
