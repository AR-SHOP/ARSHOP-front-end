package com.arthe100.arshop.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.fragments.*

abstract class BaseFragment : Fragment(), ILoadFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun toString(): String {
        return super.toString()
    }

    override fun loadFragment(klass: Class<out Fragment>) {

        try {
            val fragment = requireActivity()
                .supportFragmentManager
                .fragmentFactory
                .instantiate(requireActivity().classLoader , klass.name)

            if(isMain())
                activity?.intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.toString())
                .addToBackStack(null)
                .commit()
        }catch (throwable: Throwable){
            Log.d(klass.simpleName , throwable.toString())
        }
    }


    fun isMain() : Boolean {
        return this is HomeFragment ||
            this is CartFragment ||
            this is CategoriesFragment ||
            this is LoginFragment ||
            this is ProfileFragment
    }
}