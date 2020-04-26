package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.*
import com.arthe100.arshop.R
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*

class PhoneNumberFragment : Fragment(), ILoadFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.phone_number_fragment_layout, container, false)
    }

    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }


    override fun toString(): String {
        return "Phone Number Fragment"
    }


}
