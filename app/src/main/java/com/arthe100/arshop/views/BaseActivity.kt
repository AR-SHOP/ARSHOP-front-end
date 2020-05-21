package com.arthe100.arshop.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R

abstract class BaseActivity : AppCompatActivity(), ILoadFragment {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }
    override fun loadFragment(klass: Class<out Fragment>) {

        val fragment = supportFragmentManager
            .fragmentFactory
            .instantiate(classLoader , klass.name)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun toString(): String {
        return super.toString()
    }
}