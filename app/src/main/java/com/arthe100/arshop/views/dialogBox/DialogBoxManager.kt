package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import com.arthe100.arshop.R

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION }
enum class ButtonNumber { TWO, ONE, NONE}

object DialogBoxManager {

    fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        val alert = AlertDialog.Builder(activity, R.style.DialogStyle)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                alert.setView(R.layout.dialog_simple_layout)
            }

            MessageType.SUCCESS.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
            }

            MessageType.ERROR.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
            }

            MessageType.CAUTION.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
            }
        }

        return alert.create()
    }

    fun createDialog(activity: Activity?, messageType: MessageType, message: String?): Dialog {
        val alert = AlertDialog.Builder(activity)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                alert.setTitle(R.string.loading_title)
                    .setMessage(message)
            }

            MessageType.SUCCESS.name -> {
                alert.setTitle(R.string.success_title)
                    .setMessage(message)
                    .setPositiveButton(R.string.dialog_positive_button) { DialogInterface,
                            i -> //btn activity here
                    }
            }

            MessageType.ERROR.name -> {
                alert.setTitle(R.string.error_title)
                    .setMessage(message)
                    .setPositiveButton(R.string.dialog_positive_button) { DialogInterface,
                            i -> //btn activity here
                    }
            }
        }

        return alert.create()
    }
}
