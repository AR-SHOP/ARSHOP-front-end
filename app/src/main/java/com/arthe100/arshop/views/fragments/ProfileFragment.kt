package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.arthe100.arshop.R
import kotlinx.android.synthetic.main.activity_main_layout.*

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun toString(): String {
        return "Profile"
    }
}
