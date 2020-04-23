package com.arthe100.arshop.scripts.messege

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.arthe100.arshop.R
import javax.inject.Singleton

@Singleton
class MessageManager (var context: Context) {

    fun toast(msg : String){
        val toast = Toast.makeText(context, msg , Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showDialog(dialogTitle: String?, message: String?) {

        MaterialDialog(context).show {
            title(null, dialogTitle)
            message(null, message)
            positiveButton(null, context.resources.getString(R.string.ok))
            negativeButton(null, context.resources.getString(R.string.cancel))
        }

    }


}
