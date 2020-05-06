package com.arthe100.arshop.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication

abstract class BaseFragment : Fragment(), ILoadFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun loadFragment(fragment: Fragment?) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment!!, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()
    }
}