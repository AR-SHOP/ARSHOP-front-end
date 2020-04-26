package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arthe100.arshop.R
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.verify_fragment_layout.*

class VerifyFragment : Fragment(), ILoadFragment {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.bottom_navbar.visibility = View.INVISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.verify_fragment_layout, container, false)
    }

    override fun onStart() {
        verify_continue_btn.setOnClickListener {
            loadFragment(SignUpPasswordFragment())
        }
        super.onStart()
    }

    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }
}
