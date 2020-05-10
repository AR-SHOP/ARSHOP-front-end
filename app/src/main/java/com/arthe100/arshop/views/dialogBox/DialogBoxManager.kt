package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.arthe100.arshop.R

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION }
enum class ButtonNumber { TWO, ONE, NONE}

object DialogBoxManager {

    fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        val alert = AlertDialog.Builder(activity, R.style.DialogStyle)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                alert.setView(R.layout.dialog_simple_layout)
                alert.setCancelable(false)
//                setMessage(activity, MessageType.LOAD)
            }

            MessageType.SUCCESS.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
//                setMessage(activity, MessageType.SUCCESS)
            }

            MessageType.ERROR.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
//                setMessage(activity, MessageType.ERROR)
            }

            MessageType.CAUTION.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
//                setMessage(activity, MessageType.CAUTION)
            }
        }

        return alert.create()
    }

    fun createDialog(activity: Activity?, messageType: MessageType, message: String?): Dialog {
        val alert = AlertDialog.Builder(activity, R.style.DialogStyle)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                alert.setView(R.layout.dialog_simple_layout)
                alert.setCancelable(false)
                setMessage(activity, MessageType.LOAD, message)
            }

            MessageType.SUCCESS.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
                setMessage(activity, MessageType.SUCCESS, message)
            }

            MessageType.ERROR.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
                setMessage(activity, MessageType.ERROR, message)
            }

            MessageType.CAUTION.name -> {
                alert.setView(R.layout.dialog_one_button_layout)
                setMessage(activity, MessageType.CAUTION, message)
            }
        }

        return alert.create()
    }

    private fun setMessage(activity: Activity?, messageType: MessageType) {
        val textView: TextView

        when (messageType.name) {
            MessageType.LOAD.name -> {
                textView = activity?.findViewById(R.id.dialog_simple_layout_textView) as TextView
                textView.setText(R.string.loading_message)
            }

            MessageType.SUCCESS.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.setText(R.string.success_message)
            }

            MessageType.ERROR.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.setText(R.string.error_message)
            }

            MessageType.CAUTION.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.setText(R.string.caution_message)
            }
        }
    }

    private fun setMessage(activity: Activity?, messageType: MessageType, message: String?) {
        val textView: TextView

        when (messageType.name) {
            MessageType.LOAD.name -> {
                textView = activity?.findViewById(R.id.dialog_simple_layout_textView) as TextView
                textView.text = message
            }

            MessageType.SUCCESS.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.text = message
            }

            MessageType.ERROR.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.text = message
            }

            MessageType.CAUTION.name -> {
                textView = activity?.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                textView.text = message
            }
        }
    }

    private fun setPositiveButtonAction(activity: Activity, messageType: MessageType) {
        val button: Button

        when (messageType.name) {
            MessageType.SUCCESS.name -> {
                button = activity.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                button.setOnClickListener {

                }
            }

            MessageType.ERROR.name -> {
                button = activity.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                button.setOnClickListener {

                }
            }

            MessageType.CAUTION.name -> {
                button = activity.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
                button.setOnClickListener{

                }
            }
        }
    }
}
