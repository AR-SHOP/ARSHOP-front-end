package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arthe100.arshop.R
import kotlinx.android.synthetic.main.activity_main_layout.*

class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.bottom_navbar.visibility = View.VISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }

}
