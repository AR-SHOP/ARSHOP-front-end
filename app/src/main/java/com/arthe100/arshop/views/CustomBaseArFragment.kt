package com.arthe100.arshop.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arthe100.arshop.views.interfaces.ILoadFragment
import com.google.ar.core.Session
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.ux.ArFragment

abstract class CustomBaseArFragment : ArFragment() {

    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }
}