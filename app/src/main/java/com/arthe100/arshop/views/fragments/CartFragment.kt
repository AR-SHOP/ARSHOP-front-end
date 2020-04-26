package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.arthe100.arshop.R
import kotlinx.android.synthetic.main.activity_main_layout.*

class CartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.bottom_navbar.visibility = View.VISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cart_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun toString(): String {
        return "Cart"
    }


}
