package com.arthe100.arshop.views.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.scopes.AppScope
import kotlinx.android.synthetic.main.dialog_caution_layout.*
import kotlinx.android.synthetic.main.dialog_caution_layout.caution_positive_btn
import kotlinx.android.synthetic.main.dialog_comment_layout.*
import kotlinx.android.synthetic.main.dialog_error_layout.*
import kotlinx.android.synthetic.main.dialog_error_layout.error_card_view
import kotlinx.android.synthetic.main.dialog_load_layout.*
import kotlinx.android.synthetic.main.dialog_success_layout.*
import javax.inject.Inject

enum class MessageType { LOAD, SUCCESS, ERROR, CAUTION, COMMENT }
@AppScope
class DialogBoxManager @Inject constructor() {
    private lateinit var dialog: Dialog
    private lateinit var startAnimation: Animation
    private lateinit var clearAnimation: Animation

    fun showDialog(context: Context, messageType: MessageType, message: String = "") {

        if (this::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
            dialog = createDialog(context, messageType)
            dialog.show()
        } else {
            dialog = createDialog(context, messageType)
            dialog.show()
        }
    }

    private fun createDialog(context: Context, messageType: MessageType,
                             message: String = ""): Dialog {
        startAnimation = AnimationUtils.loadAnimation(context, R.anim.from_small)
        clearAnimation = AnimationUtils.loadAnimation(context, R.anim.from_large)
        val resultDialog = Dialog(context)
        when (messageType.name) {
            MessageType.LOAD.name -> {
                resultDialog.setContentView(R.layout.dialog_load_layout)
            }

            MessageType.ERROR.name -> {
                resultDialog.setContentView(R.layout.dialog_error_layout)
                resultDialog.error_positive_btn.setOnClickListener {
                    resultDialog.cancel()
                }
                if (message != "")
                    resultDialog.error_text.text = message
            }

            MessageType.SUCCESS.name -> {
                resultDialog.setContentView(R.layout.dialog_success_layout)
                resultDialog.success_positive_btn.setOnClickListener {
                    resultDialog.cancel()
                }
                if (message != "")
                    resultDialog.success_text.text = message
            }

            MessageType.CAUTION.name -> {
                resultDialog.setContentView(R.layout.dialog_caution_layout)
                resultDialog.caution_positive_btn.setOnClickListener {
                    resultDialog.cancel()
                }
                if (message != "")
                    resultDialog.caution_text.text = message
            }

            MessageType.COMMENT.name -> {
                resultDialog.setContentView(R.layout.dialog_comment_layout)
                resultDialog.close_btn?.setOnClickListener {
                    resultDialog.cancel()
                }
            }
        }

        resultDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        resultDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return resultDialog
    }

    fun cancel() {
        if (this::dialog.isInitialized && dialog.isShowing) dialog.dismiss()
    }
}
