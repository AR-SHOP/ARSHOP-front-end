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
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.login_fragment_layout.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), ILoadFragment {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var session: UserSession
    @Inject lateinit var fragmentFactory: FragmentFactory
    lateinit var phoneNumberFragment: PhoneNumberFragment
    lateinit var profileFragment: ProfileFragment
    var inCartFragment: Boolean = false

    private val TAG = LoginFragment::class.simpleName

    private lateinit var model: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneNumberFragment = fragmentFactory.create<PhoneNumberFragment>()
        profileFragment = fragmentFactory.create<ProfileFragment>()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
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
                DialogBoxManager.createDialog(activity, MessageType.SUCCESS).show()
                session.saveUser(state.user)
                loadFragment(profileFragment)
            }
            is AuthState.Failure -> {
                loading_bar.visibility = View.INVISIBLE
                DialogBoxManager.createDialog(activity, MessageType.ERROR).show()
            }
        }
    }


    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//         Inflate the layout for this fragment
//        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        new_acc_link.setOnClickListener{

            val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val user = pref.getString("userData" , null)

            if(user == null)
                loadFragment(phoneNumberFragment)
            else {
                DialogBoxManager.createDialog(activity, MessageType.ERROR,
                    "already logged in! user: ${Gson().fromJson(user , User.User::class.java).username}")
                    .show()
            }
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

    override fun toString(): String {
        return "Login"
    }
}
