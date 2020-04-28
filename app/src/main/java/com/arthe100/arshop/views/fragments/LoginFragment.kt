package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
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
    @Inject lateinit var phoneNumberFragment: PhoneNumberFragment
    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var session: UserSession

    private val TAG = LoginFragment::class.simpleName

    private lateinit var model: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(activity!! , viewModelProviderFactory).get(AuthViewModel::class.java)

        model.currentViewState.observe(this , Observer(::render))
    }

    private fun render(state: AuthState){
        when(state){
            is AuthState.Idle -> {
                loading_bar.visibility = View.INVISIBLE
            }
            is AuthState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE
            }
            is AuthState.LoginSuccess -> {
                loading_bar.visibility = View.INVISIBLE
                session.saveUser(state.user)
                messageManager.toast(context!! , "Welcome ${state.user.username}")
            }
            is AuthState.Failure -> {
                loading_bar.visibility = View.INVISIBLE
                messageManager.toast(context!! , state.err.toString())
            }
        }
    }


    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        new_acc_link.setOnClickListener{

            val pref = PreferenceManager.getDefaultSharedPreferences(context!!)
            val user = pref.getString("userData" , null)

            if(user == null)
                loadFragment(phoneNumberFragment)
            else
                messageManager.toast(context!! , "already logged in! user: ${Gson().fromJson(user , User.User::class.java).username}")
        }

        verify_continue_btn.setOnClickListener {
            model.onEvent(AuthUiAction.LoginAction(login_password.text.toString() , username.text.toString()))
        }

        var passwordVisible = false

        visibility_icon.setOnClickListener {
            if (passwordVisible) {
                login_password.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
                else {
                login_password.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }
    }


    override fun loadFragment(fragment: Fragment?) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment!!, fragment.toString())
                .addToBackStack(fragment.tag)
                .commit()
    }

    override fun toString(): String {
        return "Login"
    }

}
