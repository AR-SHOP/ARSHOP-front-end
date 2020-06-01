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
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.change_password_fragment_layout.*
import javax.inject.Inject

class ChangePasswordFragment @Inject constructor(
    private val userSession: UserSession
): BaseFragment(){

    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ChangePasswordFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        return inflater.inflate(R.layout.change_password_fragment_layout, container, false)
    }

    override fun render(state: ViewState) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(change_password_toolbar)
    }

    override fun onStart() {
        super.onStart()

        save_changes_pass.setOnClickListener {
            when (userSession.user) {
                is User.User -> {
                    if ((userSession.user as User.User).password == previous_pass_editText.text.toString()) {
                        if (new_pass_editText.text.toString() == repeat_new_pass_editText.text.toString()) {
                            (userSession.user as User.User).password =
                                new_pass_editText.text.toString()
                        }
                        else {
                            dialogBox.showDialog(requireContext(), MessageType.ERROR, "تکرار رمز عبور جدید مطابقت ندارد.")
                        }
                    }
                    else {
                        dialogBox.showDialog(requireContext(), MessageType.ERROR, "رمز عبور قبلی مطابقت ندارد.")
                    }
                }

                is User.GuestUser -> {
                    dialogBox.showDialog(requireContext(), MessageType.ERROR, "ابتدا وارد سیستم شوید.")
                }
            }
        }

        cancel_changes_pass.setOnClickListener {
            loadFragment(ProfileInfoFragment::class.java)
        }
    }

    override fun toString(): String {
        return "ChangePassword"
    }
}