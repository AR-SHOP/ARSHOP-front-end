package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_info_fragment_layout.*
import javax.inject.Inject

class ProfileInfoFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val userSession: UserSession): BaseFragment(){

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ProfileInfoFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        profileViewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.profile_info_fragment_layout, container, false)
    }

    override fun render(state: ViewState) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(profile_info_toolbar)
    }

    override fun onStart() {
        super.onStart()

        when (userSession.user) {
            is User.User -> {

            }

            is User.GuestUser -> {
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "ابتدا وارد سیستم شوید.")
            }
        }


        edit_profile_info_fab.setOnClickListener {
            loadFragment(EditProfileInfoFragment::class.java)
        }

        increase_balance_btn.setOnClickListener {
            loadFragment(ChargeAccountFragment::class.java)
        }

        change_password_btn.setOnClickListener {
            loadFragment(ChangePasswordFragment::class.java)
        }
    }

    override fun toString(): String {
        return "ProfileInfoFragment"
    }
}