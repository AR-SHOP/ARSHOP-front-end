package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_info_fragment_layout.*
import javax.inject.Inject

class ProfileInfoFragment @Inject constructor(
    private val userSession: UserSession
): BaseFragment(){

    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ChangePasswordFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.profile_info_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        when (userSession.user) {
            is User.User -> {
                name_last_name_textView.text = (userSession.user as User.User).username
                account_balance_info_display.text = "1000"
                phone_number_info_display.text = (userSession.user as User.User).phone
                email_info_display.text = (userSession.user as User.User).email
                NID_info_display.text = "1090807255"
                birth_date_info_display.text = "16/03/1374"
                card_number_info_display.text = "6037-1255-7884-3321"
            }

            is User.GuestUser -> {
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "ابتدا وارد سیستم شوید.")
            }
        }


        edit_profile_info.setOnClickListener {
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