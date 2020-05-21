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


    override fun toString(): String {
        return super.toString()
    }

    override fun loadFragment(klass: Class<out Fragment>) {

        val fragment = requireActivity()
            .supportFragmentManager
            .fragmentFactory
            .instantiate(requireActivity().classLoader , klass.name)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()
    }
}