package com.arthe100.arshop.views.adapters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.arthe100.arshop.R
import com.arthe100.arshop.views.fragments.CartFragment
import com.arthe100.arshop.views.fragments.CategoriesFragment
import com.arthe100.arshop.views.fragments.HomeFragment
import com.arthe100.arshop.views.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main_layout.*


class BottomNavigationViewAdapter (private var activity: FragmentActivity,
                                   private var savedInstanceState: Bundle?) {

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
                    selectedFragment = ProfileFragment()
                    activity.toolbar_container.visibility = View.INVISIBLE
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun loadFragment(selectedFragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
    }
}