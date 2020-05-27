package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.edit_profile_info_fragment_layout.*
import javax.inject.Inject

class EditProfileInfoFragment @Inject constructor(
    private val userSession: UserSession
): BaseFragment(){

    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = EditProfileInfoFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        return inflater.inflate(R.layout.edit_profile_info_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(edit_profile_toolbar)
    }

    override fun onStart() {
        super.onStart()

        when (userSession.user) {
            is User.User -> {
                name_last_name_editText.setText((userSession.user as User.User).username)
                phone_number_editText.setText((userSession.user as User.User).phone)
                email_editText.setText((userSession.user as User.User).email)
                NID_editText.setText("1090807255")
                birth_date_editText.setText("16/03/1374")
                card_number_editText.setText("6037-1255-7884-3321")
            }
        }

        save_changes.setOnClickListener {
            (userSession.user as User.User).username = name_last_name_editText.text.toString()
            (userSession.user as User.User).phone = phone_number_editText.text.toString()
            (userSession.user as User.User).email = email_editText.text.toString()
        }

        cancel_changes.setOnClickListener {
            loadFragment(ProfileInfoFragment::class.java)
        }
    }

    override fun toString(): String {
        return "EditProfileInfoFragment"
    }
}