package com.arthe100.arshop.views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.fragments.*
import com.google.android.material.badge.BadgeDrawable
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main_layout.*
import java.util.*
import javax.inject.Inject
import kotlin.system.exitProcess


class MainActivity : BaseActivity(), ILoadFragment {


    private val TAG : String? = MainActivity::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

    private var selectedFragment: Fragment? = HomeFragment()
    private var selectedItemIdStack: Stack<Int> = Stack()



    override fun inject() {
        (application as BaseApplication)
                .mainComponent(this)
                .inject(this) // i want to get injected
    }

    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
        setBottomNavigationView(savedInstanceState)
    }

    private fun setBottomNavigationView(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            selectedItemIdStack.push(bottom_navbar.selectedItemId)
            loadFragment(selectedFragment)
        }


        bottom_navbar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = CategoriesFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = CartFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = LoginFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        var bottomNavbarFragments =
            arrayListOf("Home", "Categories", "Cart", "Login", "Profile")
        selectedFragment = getTheLastFragment()
        var fragmentTag = selectedFragment!!.tag
        Log.v("fragmentTag", fragmentTag)

        if (fragmentTag in bottomNavbarFragments) {
            selectedItemIdStack.pop()
            if (selectedItemIdStack.size >= 1) {
                bottom_navbar.selectedItemId = selectedItemIdStack.peek()
                supportFragmentManager.popBackStack()
                selectedFragment = getTheLastFragment()
                super.onBackPressed()
            }
            else {
                this.finish()
                exitProcess(0)
            }
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun getTheLastFragment() : Fragment? {
        var backStackSize = supportFragmentManager.backStackEntryCount
        val fragmentTag: String? =
            supportFragmentManager.getBackStackEntryAt(backStackSize - 1).name
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }

    override fun loadFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment!!, fragment.toString())
                .addToBackStack(fragment.tag)
                .commit()
    }
}
