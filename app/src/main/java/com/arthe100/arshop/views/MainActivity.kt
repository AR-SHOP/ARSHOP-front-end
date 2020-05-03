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


class MainActivity : BaseActivity(){


    private val TAG : String? = MainActivity::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment
    @Inject lateinit var fragmentFactory: FragmentFactory
    lateinit var homeFragment: HomeFragment
    lateinit var categoriesFragment: CategoriesFragment
    lateinit var cartFragment: CartFragment
    lateinit var loginFragment: LoginFragment


    private var selectedFragment: Fragment? = null
    private var selectedItemIdStack: Stack<Int> = Stack()



    override fun inject() {
        (application as BaseApplication)
                .mainComponent(this)
                .inject(this) // i want to get injected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        homeFragment = fragmentFactory.create<HomeFragment>()
        categoriesFragment = fragmentFactory.create<CategoriesFragment>()
        cartFragment = fragmentFactory.create<CartFragment>()
        loginFragment = fragmentFactory.create<LoginFragment>()
        selectedFragment = homeFragment

        setBottomNavigationView(savedInstanceState)
    }

    override fun onBackPressed() {
        var bottomNavbarFragments =
            arrayListOf("Home Fragment", "Categories Fragment", "Cart Fragment",
                "Login Fragment", "Profile Fragment")
        selectedFragment = getTheLastFragment()
        var fragmentTag = selectedFragment!!.tag

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

    override fun toString(): String {
        return "Main Activity"
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
                    selectedFragment = homeFragment
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = categoriesFragment
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = cartFragment
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = loginFragment
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun getTheLastFragment() : Fragment? {
        var backStackSize = supportFragmentManager.backStackEntryCount
        val fragmentTag: String? =
            supportFragmentManager.getBackStackEntryAt(backStackSize - 1).name
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }

}
