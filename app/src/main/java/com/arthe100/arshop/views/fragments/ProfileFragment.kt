package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Profile.ProfileState
import com.arthe100.arshop.scripts.mvi.Profile.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_fragment_layout.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var session: UserSession

    private lateinit var model: ProfileViewModel
    private val TAG = ProfileFragment::class.simpleName

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        model.currentViewState.observe(this , Observer(::render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)
    }

    private fun render(state: ProfileState){
        when(state){
            is ProfileState.GetProfileFailure -> {
                loading_bar.visibility = View.INVISIBLE
                DialogBoxManager.createDialog(activity, MessageType.ERROR, "$state: ${state.throwable}").show()
            }

            is ProfileState.GetProfileSuccess -> {
                loading_bar.visibility = View.INVISIBLE
                DialogBoxManager.createDialog(activity, MessageType.SUCCESS).show()

                val user = state.userInfo

                name.text = if(user.fName.isEmpty())
                    "نام و نام‌خانوادگی"
                else
                    "${state.userInfo.fName} ${state.userInfo.lName}"

                email.text = if(user.email.isEmpty())
                    "ایمیل"
                else
                    state.userInfo.email

                phone_number.text = if(user.phone.isNullOrEmpty())
                    "شماره موبایل"
                else
                    state.userInfo.phone
            }

            is ProfileState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE

                DialogBoxManager.createDialog(activity, MessageType.LOAD).show()
            }
        }
    }

    override fun onStart() {
        //do what you want with 'name', 'email', 'phone_number' text views here
        super.onStart()

        model.onEvent(ProfileUiAction.GetHomePageProfileAction)
    }

    override fun toString(): String {
        return "Profile"
    }
}