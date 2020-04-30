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
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Profile.ProfileState
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.sign_up_password_fragment.loading_bar
import javax.inject.Inject


class ProfileFragment : BaseFragment() {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var session: UserSession

    private val TAG = ProfileFragment::class.simpleName

    private lateinit var model: ProfileViewModel

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
                messageManager.toast(requireActivity() , state.throwable.toString())
                loading_bar.visibility = View.INVISIBLE
            }

            is ProfileState.GetProfileSuccess -> {
                messageManager.toast(requireContext() ,
                    "" + state.userInfo.username +
                            " " + state.userInfo.password +
                            " " + state.userInfo.phone
                )
                loading_bar.visibility = View.INVISIBLE
            }

            is ProfileState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        //do what you want with 'name', 'email', 'phone_number' text views here

        super.onStart()
    }

    override fun toString(): String {
        return "Profile"
    }
}