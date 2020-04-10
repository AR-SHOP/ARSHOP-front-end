package com.arthe100.arshop.views.adapters

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.arthe100.arshop.R
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main_layout.*
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener

class SearchViewAdapter (var activity: FragmentActivity) : AppCompatActivity() {


    var searchView: MaterialSearchView = MaterialSearchView(activity)
        private set

    public fun setSearchView() : SearchViewAdapter {
        searchView = activity.search_view
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