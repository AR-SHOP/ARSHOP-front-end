package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseActivity
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.activity_main_layout.view.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class HomeFragment: BaseFragment(), ILoadFragment {

    @Inject lateinit var customArFragment: CustomArFragment
    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
                .inject(this)
        Log.v("abcd", "true")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onStart() {
        ar_btn.setOnClickListener {
            loadFragment(customArFragment)
            ar_btn.visibility = View.INVISIBLE
            activity!!.bottom_navbar.visibility = View.INVISIBLE
            activity!!.search_view.visibility = View.INVISIBLE
            activity!!.ar_buttons.visibility = View.VISIBLE

            activity!!.table_btn.setOnClickListener {
                customArFragment.setUri(customArFragment.tableUrl)
            }
            activity!!.bed_btn.setOnClickListener {
                customArFragment.setUri(customArFragment.bedUrl)
            }
            activity!!.duck_btn.setOnClickListener {
                customArFragment.setUri(customArFragment.duckUrl)
            }
        }
        super.onStart()
    }

    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    override fun toString(): String {
        return "Home"
    }
}
