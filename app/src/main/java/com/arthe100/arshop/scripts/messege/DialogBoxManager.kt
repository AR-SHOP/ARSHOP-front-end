package com.arthe100.arshop.scripts.messege

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import javax.inject.Singleton

@Singleton
class DialogBoxManager {
    fun createDialog(
        activity: Activity?, title: String?,
        message: String?, hasPosBtn: Boolean, hasNegBtn: Boolean
    ): Dialog {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle(title)
            .setMessage(message)
        if (hasPosBtn) alert.setPositiveButton("Ok") { dialog, which -> }
        if (hasNegBtn) alert.setNegativeButton("Cancel") { dialog, which -> }
        return alert.create()
    }
}