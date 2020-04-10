package com.arthe100.arshop.views

import android.graphics.Color
import android.os.Bundle
import android.view.FrameMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.arthe100.arshop.views.adapters.SearchViewAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener
import kotlinx.android.synthetic.main.activity_main_layout.*


class MainActivity : AppCompatActivity() {


    private lateinit var selectedFragment: Fragment
    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)


        selectedFragment = BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView().selectedFragment
        initializeToolBar()
        searchView = SearchViewAdapter(this, selectedFragment)
                .setSearchView().searchView
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
        toolbar.setTitleTextColor(resources.getColor(R.color.colorSecondary))
    }

}