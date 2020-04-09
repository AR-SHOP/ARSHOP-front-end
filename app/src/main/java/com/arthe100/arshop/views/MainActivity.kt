package com.arthe100.arshop.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
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

        var bottomNavBar = BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView()
    }
}