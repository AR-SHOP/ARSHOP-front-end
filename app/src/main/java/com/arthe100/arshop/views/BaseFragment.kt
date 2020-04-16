package com.arthe100.arshop.views

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

}