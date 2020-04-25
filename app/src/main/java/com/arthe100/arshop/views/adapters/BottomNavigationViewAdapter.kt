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

    private var bottomNavBar = activity.bottom_navbar
    var selectedFragment: Fragment = Fragment()
        private set

    init {
        if (savedInstanceState == null) {
            activity!!.bottom_navbar.selectedItemId = R.id.btm_navbar_home
            selectedFragment = HomeFragment()
            loadFragment(selectedFragment)
        }
    }

    public fun setBottomNavigationView() {
        bottomNavBar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                    Log.v("id", "${activity.bottom_navbar.selectedItemId}")
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = CategoriesFragment()
                    Log.v("id", "${activity.bottom_navbar.selectedItemId}")
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = CartFragment()
                    Log.v("id", "${activity.bottom_navbar.selectedItemId}")
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = LoginFragment()
                    Log.v("id", "${activity.bottom_navbar.selectedItemId}")
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }

    }



    override fun loadFragment(fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}