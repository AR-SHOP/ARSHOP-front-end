package com.arthe100.arshop.views

import `in`.srain.cube.views.GridViewWithHeaderAndFooter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.fragments.*
import kotlinx.android.synthetic.main.activity_main_layout.*
import javax.inject.Inject
import kotlin.system.exitProcess


class MainActivity : BaseActivity(), ILoadFragment {

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession
    lateinit var homeFragment: HomeFragment
    lateinit var categoriesFragment: CategoriesFragment
    lateinit var cartFragment: CartFragment
    lateinit var loginFragment: LoginFragment
    lateinit var profileFragment: ProfileFragment

    private var backPressedTime: Long = 0
    private var selectedFragment: Fragment? = null
    private val TAG : String? = MainActivity::class.simpleName


    override fun inject() {
        (application as BaseApplication)
                .mainComponent(this)
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
        homeFragment = fragmentFactory.create()
        categoriesFragment = fragmentFactory.create()
        cartFragment = fragmentFactory.create()
        loginFragment = fragmentFactory.create()
        profileFragment = fragmentFactory.create()
        setBottomNavigationView(savedInstanceState)
    }

    private fun setBottomNavigationView(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            selectedFragment = homeFragment
            loadFragment(selectedFragment)
        }

        bottom_navbar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = homeFragment
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = categoriesFragment
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = cartFragment
                }
                R.id.btm_navbar_profile -> {
                    when (session.user){
                        is User.User ->{
                            selectedFragment = profileFragment
                        }

                        is User.GuestUser ->{
                            selectedFragment = loginFragment
                        }
                    }
                }
            }
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {

        selectedFragment = getTheLastFragment()

        val isMain =
            selectedFragment!! is HomeFragment ||
            selectedFragment!! is CartFragment ||
            selectedFragment!! is CategoriesFragment ||
            selectedFragment!! is LoginFragment ||
            selectedFragment!! is ProfileFragment

        if (isMain) {

            if (selectedFragment is HomeFragment) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    this.finish()
                    exitProcess(0)
                }
                else {
                    messageManager.toast(this,"برای خروج مجددا دکمه بازگشت را بزنید")
                }
            }

            supportFragmentManager.popBackStack(homeFragment.tag,0)
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            backPressedTime = System.currentTimeMillis()
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
}
