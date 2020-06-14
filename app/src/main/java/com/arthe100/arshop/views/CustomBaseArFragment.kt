package com.arthe100.arshop.views

import android.view.View
import android.view.WindowManager
import com.google.ar.sceneform.ux.ArFragment


abstract class CustomBaseArFragment : ArFragment() {


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val activity = activity
        if (hasFocus && activity != null) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}