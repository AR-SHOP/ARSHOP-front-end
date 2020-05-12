package com.arthe100.arshop.views.dialogBox

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.arthe100.arshop.R
import javax.inject.Singleton

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION }

@Singleton
class DialogBoxManager() {

    fun createDialog(activity: Activity?, messageType: MessageType): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogStyle)

        when (messageType.name) {
            MessageType.LOAD.name -> {
                builder.setView(R.layout.dialog_load_layout)
            }

            MessageType.SUCCESS.name -> {
                builder.setView(R.layout.dialog_success_layout)
            }

            MessageType.ERROR.name -> {
                builder.setView(R.layout.dialog_error_layout)
            }

            MessageType.CAUTION.name -> {
                builder.setView(R.layout.dialog_caution_layout)
            }
        }

        return builder.create()
    }

//    @SuppressLint("InflateParams")
//    fun createDialog(activity: Activity?, context: Context, messageType: MessageType, message: String?): Dialog {
//        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val builder = AlertDialog.Builder(activity, R.style.DialogStyle)
//        val view: View
//        val textView: TextView
//        val posBtn: Button
//        val negBtn: Button
//
//        when (messageType.name) {
//            MessageType.LOAD.name -> {
//                view = inflater.inflate(R.layout.dialog_load_layout, null)
//                textView = view.findViewById(R.id.dialog_simple_layout_textView) as TextView
//                textView.text = message
//
//                builder.setView(view)
//            }
//
//            MessageType.SUCCESS.name -> {
//                view = inflater.inflate(R.layout.dialog_success_layout, null)
//
//                textView = view.findViewById(R.id.dialog_one_button_layout_textView) as TextView
//                textView.text = message
//
//                posBtn = view.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
//                posBtn.setOnClickListener {
//
//                }
//
//                builder.setView(view)
//            }
//
//            MessageType.ERROR.name -> {
//                view = inflater.inflate(R.layout.dialog_success_layout, null)
//                textView = view.findViewById(R.id.dialog_one_button_layout_textView) as TextView
//                textView.text = message
//
//                posBtn = view.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
//                posBtn.setOnClickListener {
//
//                }
//
//                builder.setView(view)
//            }
//
//            MessageType.CAUTION.name -> {
//                view = inflater.inflate(R.layout.dialog_success_layout, null)
//                textView = view.findViewById(R.id.dialog_one_button_layout_textView) as TextView
//                textView.text = message
//
//                posBtn = view.findViewById(R.id.dialog_one_button_layout_posBtn) as Button
//                posBtn.setOnClickListener {
//
//                }
//
//                builder.setView(view)
//            }
//        }
//
//        return builder.create()
//    }
}
