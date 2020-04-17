package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment

class ProfileFragment : BaseFragment() {

    private val TAG = ProfileFragment::class.simpleName


    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)

    }

    override fun onStart() {
        super.onStart()

//        lifecycleScope.launch {
//            userRepository.getUsers().onEach{
//                Log.v(TAG , it.toString() + "\n")
//            }
//        }
    }

    override fun toString(): String {
        return "Profile"
    }

}
