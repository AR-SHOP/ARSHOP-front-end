package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arthe100.arshop.R
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.login_fragment_layout.*

class LoginFragment : Fragment(), ILoadFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.login_fragment_layout, container, false)

        if (view != null) {
        }

        return view
    }

    override fun onStart() {
        new_acc_link.setOnClickListener{
            loadFragment(SignUpFragment())
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

}
