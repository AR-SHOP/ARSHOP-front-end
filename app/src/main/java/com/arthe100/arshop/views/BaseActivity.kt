package com.arthe100.arshop.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.views.fragments.*
import java.lang.NullPointerException

abstract class BaseActivity : AppCompatActivity(), ILoadFragment {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }
    override fun loadFragment(klass: Class<out Fragment>) {

        val fragment = supportFragmentManager
            .fragmentFactory
            .instantiate(classLoader , klass.name) as? BaseFragment
            ?: throw ClassCastException("${klass.simpleName} is a subclass of BaseFragment!")

        if(fragment.isMain())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun toString(): String {
        return super.toString()
    }

}