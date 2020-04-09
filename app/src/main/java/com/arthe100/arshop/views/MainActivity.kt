package com.arthe100.arshop.views

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.arthe100.arshop.R
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener
import kotlinx.android.synthetic.main.activity_main_layout.*


class MainActivity : AppCompatActivity() {


    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView()

        var toolbar: Toolbar = tool_bar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.RED)

        searchView = MaterialSearchView(this)
        searchView = search_view
        searchView.setCursorDrawable(R.drawable.cursor)

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        searchView.setOnSearchViewListener(object : SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_layout, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }
}