package com.arthe100.arshop.scripts.messege

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.arthe100.arshop.scripts.di.scopes.AppScope
import javax.inject.Inject
import javax.inject.Singleton

@AppScope
class MessageManager @Inject constructor() {

    fun toast(context: Context, msg : String) {
        val toast = Toast.makeText(context, msg , Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 10)
        toast.show()
    }
}
