package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import com.arthe100.arshop.R

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION }

object DialogBoxManager {
    lateinit var dialog: Dialog

    fun showDialog(activity: Activity?, messageType: MessageType) {
        if (this::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
            dialog = createDialog(activity, messageType)
            dialog.show()
            DialogSetter.set(activity,  messageType)
        } else {
            dialog = createDialog(activity, messageType)
            dialog.show()
            DialogSetter.set(activity, messageType)
        }
    }

    private fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogStyle)
        dialog = builder.create()

        when (messageType.name) {
            MessageType.LOAD.name -> {
                builder.setView(R.layout.dialog_load_layout)
                    .setCancelable(false)
            }

            MessageType.SUCCESS.name -> {
                builder.setView(R.layout.dialog_success_layout)
                    .setPositiveButton(R.string.dialog_positive_button) { dialogInterface: DialogInterface, i: Int ->

                    }
            }

            MessageType.ERROR.name -> {
                builder.setView(R.layout.dialog_error_layout)
                    .setPositiveButton(R.string.dialog_positive_button) { dialogInterface: DialogInterface, i: Int ->

                    }
            }

            MessageType.CAUTION.name -> {
                builder.setView(R.layout.dialog_caution_layout)
                    .setPositiveButton(R.string.dialog_positive_button) { dialogInterface: DialogInterface, i: Int ->

                    }
            }
        }

        return builder.create()
    }

    fun cancel() {
        if (this::dialog.isInitialized && dialog.isShowing) dialog.dismiss()
    }
}
