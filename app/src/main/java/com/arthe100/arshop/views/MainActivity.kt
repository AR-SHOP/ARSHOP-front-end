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

        var selectedFragment: Fragment = setBtmViewSelectedListener(bottom_navbar)
        loadFragment(selectedFragment, R.id.fragment_container)

    }

    private fun loadFragment(selectedFragment: Fragment, fragmentContainer: Int) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer, selectedFragment)
                .commit()
    }

    private fun setBtmViewSelectedListener(bottomNavbar: BottomNavigationView?) : Fragment {
        var selectedFragment: Fragment = Fragment()
        bottom_navbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = CategoriesFragment()
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = CartFragment()
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = ProfileFragment()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        return selectedFragment
    }

}