package com.arthe100.arshop.views.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.arthe100.arshop.R
import com.arthe100.arshop.views.fragments.HomeFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener

class SearchViewAdapter(context: HomeFragment) : AppCompatActivity() {


    lateinit var searchView: MaterialSearchView
        private set

    public fun setSearchView(toolbar: Toolbar, searchBar: MaterialSearchView) : SearchViewAdapter {
        setSupportActionBar(toolbar)
        searchView = searchBar
        searchView.setCursorDrawable(R.drawable.cursor)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.setOnSearchViewListener(searchViewListener)
        return this
    }


    private object queryTextListener: MaterialSearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            //Do some magic
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            //Do some magic
            return false
        }
    }

    private object searchViewListener: SearchViewListener {
        override fun onSearchViewClosed() {
            //Do some magic
        }

        override fun onSearchViewShown() {
            //Do some magic
        }
    }



}