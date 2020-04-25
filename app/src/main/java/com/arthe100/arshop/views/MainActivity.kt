package com.arthe100.arshop.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.arthe100.arshop.views.adapters.SearchViewAdapter
import com.arthe100.arshop.views.atoms.ButtonAV
import com.arthe100.arshop.views.fragments.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.victor.loading.newton.NewtonCradleLoading
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), ILoadFragment {


    private val TAG : String? = MainActivity::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

    private var selectedFragment: Fragment = HomeFragment()
    private var selectedItemIdStack: Stack<Int> = Stack()


    override fun inject() {
        (application as BaseApplication)
                .appComponent
                .mainComponent()
                .create(this) // add this context to the container
                .inject(this) // i want to get injected
    }

    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        if (savedInstanceState == null) {
            bottom_navbar.selectedItemId = R.id.btm_navbar_home
            selectedItemIdStack.push(bottom_navbar.selectedItemId)
            Log.v("stack_size", "${selectedItemIdStack.size}")
            loadFragment(selectedFragment)
        }

        bottom_navbar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btm_navbar_home -> {
                    selectedFragment = HomeFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                    Log.v("select", "${selectedFragment.toString()}")
                }
                R.id.btm_navbar_categories -> {
                    selectedFragment = CategoriesFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                    Log.v("select", "${selectedFragment.toString()}")
                }
                R.id.btm_navbar_cart -> {
                    selectedFragment = CartFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                    Log.v("select", "${selectedFragment.toString()}")
                }
                R.id.btm_navbar_profile -> {
                    selectedFragment = LoginFragment()
                    if (selectedItemIdStack.peek() != item.itemId) selectedItemIdStack.push(item.itemId)
                    Log.v("select", "${selectedFragment.toString()}")
                }
            }
            Log.v("stack_size", "${selectedItemIdStack.size}")
            Log.v("backStack", "${supportFragmentManager.backStackEntryCount}")
            loadFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }





//        BottomNavigationViewAdapter(this, savedInstanceState)
//                .setBottomNavigationView()



//        button.setOnClickListener {
//            MaterialDialog(this)
//                        title = "Error"
//                        message(R.string.username_hint)
//                        positiveButton(R.string.ok)
//                    }
//        }



//        initializeToolBar()
//        searchView = SearchViewAdapter(this)
//                .setSearchView().searchView

    }


    override fun onBackPressed() {
        if (selectedItemIdStack.size > 1) {
            selectedItemIdStack.pop()
            bottom_navbar.selectedItemId = selectedItemIdStack.peek()
//            Log.v("stack_size", "${selectedItemIdStack.size}")
//            Log.v("back", "${selectedFragment.toString()}")
            supportFragmentManager.popBackStack()
            Log.v("backStack", "${supportFragmentManager.backStackEntryCount}")
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_layout, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }

//    private fun initializeToolBar() {
//        var toolbar = tool_bar
//        setSupportActionBar(toolbar)
//        toolbar_container.visibility = View.VISIBLE
//    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
