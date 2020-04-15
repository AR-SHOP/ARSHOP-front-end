package com.arthe100.arshop.views.adapters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.arthe100.arshop.R
import com.arthe100.arshop.views.BaseActivity
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.fragments.*
import kotlinx.android.synthetic.main.activity_main_layout.*


class BottomNavigationViewAdapter (private var activity: BaseActivity,
                                   private var savedInstanceState: Bundle?) : ILoadFragment {

    var selectedFragment: Fragment = Fragment()
        private set

    init {
        if (savedInstanceState == null) {
            activity.bottom_navbar.selectedItemId = R.id.btm_navbar_home
            activity.toolbar_container.visibility = View.VISIBLE
            selectedFragment = HomeFragment()
            loadFragment(selectedFragment)
        }
    }

    public fun setBottomNavigationView() {
        activity.bottom_navbar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                    activity.toolbar_container.visibility = View.VISIBLE
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = CategoriesFragment()
                    activity.toolbar_container.visibility = View.VISIBLE
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = CartFragment()
                    activity.toolbar_container.visibility = View.INVISIBLE
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = LoginFragment()
                    activity.toolbar_container.visibility = View.INVISIBLE
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun loadFragment(fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }
}