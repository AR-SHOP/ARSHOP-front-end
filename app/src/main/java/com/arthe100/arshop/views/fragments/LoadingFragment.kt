package com.arthe100.arshop.views.fragments

import android.R.attr.button
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.victor.loading.newton.NewtonCradleLoading
import kotlinx.android.synthetic.main.loading_layout.*


class LoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loading_layout, container, false)
    }

//    override fun onStart() {
//        super.onStart()
//    }

    override fun toString(): String {
        return "Loading"
    }
}
