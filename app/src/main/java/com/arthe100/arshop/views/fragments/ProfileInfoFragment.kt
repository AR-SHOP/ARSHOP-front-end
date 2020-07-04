package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_info_fragment_layout.*
import kotlinx.android.synthetic.main.profile_info_fragment_layout.name
import javax.inject.Inject

class ProfileInfoFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
): BaseFragment(){

    private lateinit var model: ProfileViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ProfileInfoFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.profile_info_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(profile_info_toolbar)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()

        edit_profile_info_fab.setOnClickListener {
            loadFragment(EditProfileInfoFragment::class.java)
        }

        increase_balance_btn.setOnClickListener {
            loadFragment(ChargeAccountFragment::class.java)
        }

        change_password_btn.setOnClickListener {
            loadFragment(ChangePasswordFragment::class.java)
        }

        model.onEvent(ProfileUiAction.GetHomePageProfileAction)
    }

    override fun render(state: ViewState){
        when(state) {
            is ViewState.IdleState -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
            }

            is ViewState.Failure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, state.throwable.toString())
                messageManager.toast(requireContext(), state.throwable.toString())
            }

            is ProfileState.GetProfileSuccess -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val user = state.userInfo
                model.currentProfile = user

                name.text = user.fName + " " + user.lName

                phone_number_info_display.text = user.phone

                NID_info_display.text = user.ssId

                email_info_display.text = user.email

            }

            is ViewState.LoadingState -> {
//                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
            is ProfileState.LogoutState -> {
                loadFragment(LoginFragment::class.java)
            }
        }
    }

    override fun toString(): String {
        return "ProfileInfoFragment"
    }
}