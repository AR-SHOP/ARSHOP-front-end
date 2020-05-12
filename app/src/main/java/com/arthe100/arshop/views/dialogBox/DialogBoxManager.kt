package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.arthe100.arshop.R

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION }
enum class ButtonNumber { TWO, ONE, NONE}

object DialogBoxManager {
    private lateinit var alert: Dialog
    private lateinit var builder: AlertDialog.Builder

    fun create(activity: Activity?, messageType: MessageType) {
//        DialogMessage.setMessage(activity, messageType)
        alert = createDialog(activity, messageType)
        alert.show()
//        setPositiveButtonAction(activity, alert, messageType)
    }

    fun create(activity: Activity?, messageType: MessageType, message: String?) {
//        DialogMessage.setMessage(activity, messageType, message)
        alert = createDialog(activity, messageType)
        alert.show()
//        setPositiveButtonAction(activity, alert, messageType)
    }

    private fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        builder = AlertDialog.Builder(activity, R.style.DialogStyle)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                builder.setView(R.layout.dialog_simple_layout)
            }

            MessageType.SUCCESS.name -> {
                builder.setView(R.layout.dialog_one_button_layout)
            }

            MessageType.ERROR.name -> {
                builder.setView(R.layout.dialog_one_button_layout)
            }

            MessageType.CAUTION.name -> {
                builder.setView(R.layout.dialog_one_button_layout)
            }
        }

        return builder.create()
    }

    fun dismiss() {
        alert.dismiss()
    }
}
