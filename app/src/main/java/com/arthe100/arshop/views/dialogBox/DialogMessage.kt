package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.widget.TextView
import com.arthe100.arshop.R

object DialogMessage {

     fun setMessage(activity: Activity?, messageType: MessageType) {
        val textView: TextView

         if (activity != null) {
             when (messageType.name) {
                 MessageType.LOAD.name -> {
                     textView =
                         activity.findViewById(R.id.dialog_simple_layout_textView) as TextView
                     textView.setText(R.string.loading_message)
                 }

                 MessageType.SUCCESS.name -> {
                     textView =
                         activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                     textView.setText(R.string.success_message)
                 }

                 MessageType.ERROR.name -> {
                     textView =
                         activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                     textView.setText(R.string.error_message)
                 }

                 MessageType.CAUTION.name -> {
                     textView =
                         activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                     textView.setText(R.string.caution_message)
                 }
             }
         }
    }

    fun setMessage(activity: Activity?, messageType: MessageType, message: String?) {
        val textView: TextView

        if (activity != null) {
            when (messageType.name) {
                MessageType.LOAD.name -> {
                    textView = activity.findViewById(R.id.dialog_simple_layout_textView) as TextView
                    textView.text = message
                }

                MessageType.SUCCESS.name -> {
                    textView =
                        activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                    textView.text = message
                }

                MessageType.ERROR.name -> {
                    textView =
                        activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                    textView.text = message
                }

                MessageType.CAUTION.name -> {
                    textView =
                        activity.findViewById(R.id.dialog_one_button_layout_textView) as TextView
                    textView.text = message
                }
            }
        }
    }
}