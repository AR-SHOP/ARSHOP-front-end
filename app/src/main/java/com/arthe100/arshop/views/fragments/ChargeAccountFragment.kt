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
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.charge_account_fragment_layout.*
import javax.inject.Inject

class ChargeAccountFragment @Inject constructor(
    private val userSession: UserSession
): BaseFragment(){

    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ChargeAccountFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        return inflater.inflate(R.layout.charge_account_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        when (userSession.user) {
            is User.User -> {
                account_balance_charge_display.text = "1000"
            }
        }

        enter_bank_portal.setOnClickListener {

        }

        cancel_charge.setOnClickListener {
            loadFragment(ProfileInfoFragment::class.java)
        }
    }

    override fun toString(): String {
        return "ChargeAccount"
    }
}