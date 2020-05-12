package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.Dialog
import android.widget.Button
import com.arthe100.arshop.R

object DialogButton {

    fun setPositiveButtonAction(activity: Activity?, alert: Dialog, messageType: MessageType) {
        val button: Button

        if (activity != null) {
            when (messageType.name) {
                MessageType.SUCCESS.name -> {
                    button = activity?.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                    button.setOnClickListener {
                        alert.dismiss()
                    }
                }

                MessageType.ERROR.name -> {
                    button = activity?.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                    button.setOnClickListener {
                        alert.dismiss()
                    }
                }

                MessageType.CAUTION.name -> {
                    button = activity?.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                    button.setOnClickListener {
                        alert.dismiss()
                    }
                }
            }
        }
    }
}