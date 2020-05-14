package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.Dialog
import android.widget.Button
import android.widget.TextView
import com.arthe100.arshop.R

object DialogSetter {
    private lateinit var textView: TextView
    private lateinit var posBtn: Button

    fun set(activity: Activity?, messageType: MessageType) {
        DialogBoxManager.dialog.setOnShowListener() {
            setText(activity, DialogBoxManager.dialog, messageType)
            setPositiveButtonAction(activity, DialogBoxManager.dialog, messageType)
        }
    }

    private fun setText(activity: Activity?, dialog: Dialog, messageType: MessageType) {
        when (messageType.name) {
            MessageType.SUCCESS.name -> {
                textView = activity?.findViewById(R.id.dialog_success_textView) as TextView

                textView.text = R.string.success_message.toString()
            }
            MessageType.ERROR.name -> textView = activity?.findViewById(R.id.dialog_error_textView) as TextView
            MessageType.CAUTION.name -> textView = activity?.findViewById(R.id.dialog_caution_textView) as TextView
        }
    }

    private fun setPositiveButtonAction(activity: Activity?, dialog: Dialog, messageType: MessageType) {
        when (messageType.name) {
            MessageType.SUCCESS.name -> {
                posBtn = activity?.findViewById(R.id.dialog_success_posBtn) as Button

                posBtn.setOnClickListener() {
                    dialog.dismiss()
                }
            }

            MessageType.ERROR.name -> {
                posBtn = activity?.findViewById(R.id.dialog_error_posBtn) as Button

                posBtn.setOnClickListener() {
                    dialog.dismiss()
                }
            }

            MessageType.CAUTION.name -> {
                posBtn = activity?.findViewById(R.id.dialog_caution_posBtn) as Button

                posBtn.setOnClickListener() {
                    dialog.dismiss()
                }
            }
        }
    }
}