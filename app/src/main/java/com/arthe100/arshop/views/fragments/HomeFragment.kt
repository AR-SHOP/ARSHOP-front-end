package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.RelativeLayout.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseActivity
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.IRenderView
import com.arthe100.arshop.views.atoms.ButtonAV
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.activity_main_layout.view.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class HomeFragment: BaseFragment(), ILoadFragment, IRenderView {

    @Inject lateinit var customArFragment: CustomArFragment
    private lateinit var homeView: View
    private lateinit var btnLayoutParams: LayoutParams

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
                .inject(this)
//        Log.v("abcd", "true")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        homeView = inflater.inflate(R.layout.home_fragment_layout, container, false)
        renderView()
        return homeView
    }

    override fun renderView() {
        setLayout()
        var button = ButtonAV(requireContext()).apply {
            state.apply {
                textSize = 14f
                text = "My button"
                layoutParams = btnLayoutParams
                onClick = { action() }
            }
        }
        (homeView as ViewGroup).addView(button.getView())
    }

    private fun action() {
        return Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_SHORT).show()
    }

    private fun setLayout() {
        btnLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        btnLayoutParams.setMargins(10,10,10,10)
        btnLayoutParams.addRule(ALIGN_PARENT_BOTTOM)
        btnLayoutParams.addRule(ABOVE, R.id.bottom_navbar)
    }


    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun toString(): String {
        return "Home"
    }
}
