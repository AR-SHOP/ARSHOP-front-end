package com.arthe100.arshop.views.adapters

import android.os.Bundle
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

    private lateinit var selectedFragment: Fragment

    init {
        if (savedInstanceState == null) {
            activity.bottom_navbar.selectedItemId = R.id.btm_navbar_home
            loadFragment(HomeFragment())
        }
    }

    public fun setBottomNavigationView() {
        activity.bottom_navbar.setOnNavigationItemSelectedListener {item ->
            selectedFragment = Fragment()
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                }
                R.id.btm_navbar_categories ->
                    selectedFragment = CategoriesFragment()
                R.id.btm_navbar_cart ->
                    selectedFragment = CartFragment()
                R.id.btm_navbar_profile ->
                    selectedFragment = ProfileFragment()
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