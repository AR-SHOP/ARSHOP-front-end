package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.*
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.phone_number_fragment_layout.*
import javax.inject.Inject

class PhoneNumberFragment : BaseFragment(), ILoadFragment {

    @Inject lateinit var verifyFragment: VerifyFragment

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.phone_number_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()

        recieve_code_btn.setOnClickListener{
            loadFragment(verifyFragment)
        }

    }

    override fun loadFragment(fragment: Fragment?) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment!!)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun toString(): String {
        return "Phone Number Fragment"
    }


}
