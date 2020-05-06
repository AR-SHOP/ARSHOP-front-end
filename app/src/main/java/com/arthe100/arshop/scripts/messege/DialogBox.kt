package com.arthe100.arshop.scripts.messege

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.arthe100.arshop.R

enum class MessageType { LOAD, SUCCESS, ERROR }

object DialogBox {

    fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        val alert = AlertDialog.Builder(activity)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                alert.setTitle(R.string.loading_title)
                    .setMessage(R.string.loading_message)
            }

            MessageType.SUCCESS.name -> {
                alert.setTitle(R.string.success_title)
                    .setMessage(R.string.success_message)
                    .setPositiveButton(R.string.dialog_positive_button) { DialogInterface,
                            i -> //btn activity here
                    }
            }

            MessageType.ERROR.name -> {
                alert.setTitle(R.string.error_title)
                    .setMessage(R.string.error_message)
                    .setPositiveButton(R.string.dialog_positive_button) { DialogInterface,
                            i -> //btn activity here
                    }
            }
        }

        return alert.create()
    }
}
