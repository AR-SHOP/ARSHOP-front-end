package com.arthe100.arshop.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.di.MyFragmentFactory
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.fragments.*
import kotlinx.android.synthetic.main.activity_main_layout.*
import javax.inject.Inject
import kotlin.system.exitProcess


class MainActivity : BaseActivity(), ILoadFragment {

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var fragmentFactory: MyFragmentFactory
    @Inject lateinit var session: UserSession


    private var backPressedTime: Long = 0
    private val TAG : String? = MainActivity::class.simpleName


    override fun inject() {
        (application as BaseApplication)
                .mainComponent(this)
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main_layout)
        setBottomNavigationView(savedInstanceState)
    }

    private fun setBottomNavigationView(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            loadFragment(HomeFragment::class.java)
        }
        bottom_navbar.setOnNavigationItemSelectedListener {item ->

            val klass = when (item.itemId) {
                R.id.btm_navbar_home -> HomeFragment::class.java
                R.id.btm_navbar_categories -> CategoriesFragment::class.java
                R.id.btm_navbar_cart -> CartFragment::class.java
                R.id.btm_navbar_profile -> {
                    when (session.user){
                        is User.User -> ProfileFragment::class.java
                        else -> LoginFragment::class.java
                    }
                }
                else -> throw IllegalArgumentException("Invalid navbar state!")
            }
            loadFragment(klass)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {

        val fragment = getTheLastFragment()

        val isMain =
            fragment is HomeFragment ||
            fragment is CartFragment ||
            fragment is CategoriesFragment ||
            fragment is LoginFragment ||
            fragment is ProfileFragment


        if (isMain) {

            if (fragment is HomeFragment) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    this.finish()
                    exitProcess(0)
                }
                else {
                    messageManager.toast(this,"برای خروج مجددا دکمه بازگشت را بزنید")
                }
            }

            supportFragmentManager.popBackStack()
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            backPressedTime = System.currentTimeMillis()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun getTheLastFragment() : Fragment? {
        val backStackSize = supportFragmentManager.backStackEntryCount
        val fragmentTag: String? =
            supportFragmentManager.getBackStackEntryAt(backStackSize - 1).name
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }
}
