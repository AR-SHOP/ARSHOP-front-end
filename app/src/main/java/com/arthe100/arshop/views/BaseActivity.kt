package com.arthe100.arshop.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arthe100.arshop.R
import com.arthe100.arshop.views.interfaces.ILoadFragment

abstract class BaseActivity : AppCompatActivity(),
    ILoadFragment {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun loadFragment(klass: Class<out Fragment>) {

        val manager = supportFragmentManager

        val fragment = manager
            .fragmentFactory
            .instantiate(classLoader , klass.name) as? BaseFragment
            ?: throw ClassCastException("${klass.simpleName} is not a subclass of BaseFragment!")

        if(fragment.isMain())
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        manager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()

    }

    override fun toString(): String {
        return super.toString()
    }

}