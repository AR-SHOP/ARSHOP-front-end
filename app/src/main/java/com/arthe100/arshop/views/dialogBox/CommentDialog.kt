package com.arthe100.arshop.views.dialogBox

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CommentNetwork
import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.base.ProductUiAction
import kotlinx.android.synthetic.main.dialog_comment_layout.*

class CommentDialog(
    private val kontext: Context ,
    private val model: ProductViewModel
) : Dialog(kontext){

    private val viewId = R.layout.dialog_comment_layout

    init {
        setContentView(viewId)
        close_btn?.setOnClickListener{ close() }

        comment_confirm_btn?.setOnClickListener { confirm() }

        window!!.attributes.windowAnimations = R.style.DialogAnimation
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun close(){
        this.cancel()
    }

    fun open(){
        show()
    }


    private fun confirm(){
        val content = comment_text?.text.toString()
        val isAnonymous = if(anonymous_user.isChecked) 1 else 0
        val rating = rating_bar?.rating!!

        if(content.isEmpty()) return

        model.onEvent(
            ProductUiAction.SendCommentAction(
                CommentNetwork(
                    productId = model.product.id,
                    content = content,
                    rating = rating,
                    anonymous = isAnonymous
                )
            )
        )

        close()
    }

}