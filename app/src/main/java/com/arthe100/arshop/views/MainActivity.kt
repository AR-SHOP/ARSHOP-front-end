package com.arthe100.arshop.views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.ButtonBarLayout
import com.arthe100.arshop.R
import androidx.appcompat.widget.Toolbar
import androidx.core.content.withStyledAttributes
import androidx.fragment.app.Fragment
import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.arthe100.arshop.views.adapters.SearchViewAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject


class MainActivity : BaseActivity(), ILoadFragment {


    private val TAG : String? = MainActivity::class.simpleName
    val duckUrl = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
    val bedUrl = "https://poly.googleapis.com/downloads/fp/1586167422468753/8mkAgVYGbL4/5oNDqZI-I0J/Bed_01.gltf"

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

    override fun inject() {
        Log.d(TAG , "injecting MainActivity")
        (application as BaseApplication).appComponent.mainComponent().create(this).inject(this)
        Log.d(TAG , "injected MainActivity")
    }

    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
//        messageManager.toast(this , "hey this works")



        BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView()


        initializeToolBar()
        searchView = SearchViewAdapter(this)
                .setSearchView().searchView


        var btn: Button = Button(this)
        btn = ar_btn
        btn.setOnClickListener {
            loadFragment(customArFragment)
            btn.visibility = View.INVISIBLE
            bottom_navbar.visibility = View.INVISIBLE
            toolbar_container.visibility = View.INVISIBLE
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_layout, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initializeToolBar() {
        var toolbar: Toolbar = Toolbar(this)
        toolbar = tool_bar
        setSupportActionBar(toolbar)
        toolbar_container.visibility = View.VISIBLE
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }
}
