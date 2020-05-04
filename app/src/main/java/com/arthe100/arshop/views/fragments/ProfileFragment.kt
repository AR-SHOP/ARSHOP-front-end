package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*


class ProfileFragment : BaseFragment() {

    private val TAG = ProfileFragment::class.simpleName

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)
    }

    override fun onStart() {
        //do what you want with 'name', 'email', 'phone_number' text views here
        super.onStart()
    }

    override fun toString(): String {
        return "Profile Fragment"
    }
}
