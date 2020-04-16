package com.arthe100.arshop.scripts.messege

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import javax.inject.Singleton

@Singleton
class MessageManager {

    fun toast(context: Context, msg : String){
        val toast = Toast.makeText(context, msg , Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}