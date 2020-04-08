package com.arthe100.arshop.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.views.fragments.CartFragment
import com.arthe100.arshop.views.fragments.CategoriesFragment
import com.arthe100.arshop.views.fragments.HomeFragment
import com.arthe100.arshop.views.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_layout.*

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)


        bottom_navbar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    loadFragment(HomeFragment())
                }
                R.id.btm_navbar_categories -> {
                    loadFragment(CategoriesFragment())
                }
                R.id.btm_navbar_cart -> {
                    loadFragment(CartFragment())
                }
                R.id.btm_navbar_profile -> {
                    loadFragment(ProfileFragment())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }


        if (savedInstanceState == null) {
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            loadFragment(HomeFragment())
        }

    }

    private fun loadFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
    }

}