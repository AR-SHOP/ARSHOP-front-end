package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.login_fragment_layout.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), ILoadFragment {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var session: UserSession
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var dialogBox: DialogBoxManager

    private lateinit var messageManager: MessageManager
    private lateinit var signUpFragment: SignUpFragment
    private lateinit var profileFragment: ProfileFragment
    private val TAG = LoginFragment::class.simpleName
    private lateinit var model: AuthViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageManager = MessageManager()
        profileFragment = fragmentFactory.create<ProfileFragment>()
        signUpFragment = fragmentFactory.create<SignUpFragment>()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model.currentViewState.observe(this , Observer(::render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        login_new_account.setOnClickListener{

            val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val user = pref.getString("userData" , null)

            if(user == null)
                loadFragment(signUpFragment)
            else {
                messageManager.toast(requireContext(),
                    "already logged in! user: ${Gson().fromJson(user , User.User::class.java).username}")
            }
        }

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
                login_fragment_layout.visibility = View.VISIBLE
            }
            is AuthState.LoadingState -> {
                login_fragment_layout.visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
            is AuthState.LoginSuccess -> {
                dialogBox.cancel()
                login_fragment_layout.visibility = View.VISIBLE
                session.saveUser(state.user)
                loadFragment(profileFragment)
            }
            is AuthState.Failure -> {
                login_fragment_layout.visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR)
            }
        }
    }
}
