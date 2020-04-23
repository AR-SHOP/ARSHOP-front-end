package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseActivity
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.atoms.ButtonAV
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

        val view = inflater.inflate(R.layout.home_fragment_layout, container, false)

        var btn = ButtonAV(view.context).make()
        var params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10,10,10,10)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        params.addRule(RelativeLayout.ABOVE, R.id.bottom_navbar)
        btn.layoutParams = params
        (view as ViewGroup).addView(btn)

        return view
    }

    override fun onStart() {
        ar_btn.setOnClickListener {
            loadFragment(customArFragment)
        }
        super.onStart()
    }

    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right)
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun toString(): String {
        return "Home"
    }
}
