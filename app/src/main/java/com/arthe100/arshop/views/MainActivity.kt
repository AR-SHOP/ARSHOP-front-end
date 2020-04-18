package com.arthe100.arshop.views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.arthe100.arshop.views.adapters.SearchViewAdapter
import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.views.fragments.LoadingFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import javax.inject.Inject


class MainActivity : BaseActivity(), ILoadFragment {


    private val TAG : String? = MainActivity::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

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




        BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView()

        messageManager.toast(this, supportFragmentManager.backStackEntryCount.toString())

        initializeToolBar()
        searchView = SearchViewAdapter(this)
                .setSearchView().searchView

    }

    override fun onStart() {
        loading_btn.setOnClickListener(View.OnClickListener {
            var loadingFragment = LoadingFragment()
            when (loading_btn.text) {
                "loading" -> {
                    loadFragment(loadingFragment)
                    loading_btn.setText("stop")
                }
                "stop" -> {
                    supportFragmentManager.popBackStack()
                    loading_btn.setText("loading")
                }
            }
            var count = supportFragmentManager.backStackEntryCount
            var string = ""
            for (i in 0 until count - 1) {
                string += "${supportFragmentManager.fragments[i].toString()} - "
            }
            messageManager.toast(this, string)
        })


        super.onStart()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_layout, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initializeToolBar() {
        var toolbar = tool_bar
        setSupportActionBar(toolbar)
        toolbar_container.visibility = View.VISIBLE
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right)
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
