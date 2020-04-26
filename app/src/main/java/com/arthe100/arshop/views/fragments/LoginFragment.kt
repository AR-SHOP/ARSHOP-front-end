package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.views.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.login_fragment_layout.*
import org.w3c.dom.Text
import kotlin.math.log


class LoginFragment : Fragment(), ILoadFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity!!.bottom_navbar.visibility = View.VISIBLE


        new_acc_link.setOnClickListener{
            loadFragment(PhoneNumberFragment())
        }

        var passwordVisible = false

        visibility_icon.setOnClickListener {
            if (passwordVisible) {
                login_password.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
            else {
                login_password.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }
    }


    override fun loadFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }

    override fun toString(): String {
        return "Login"
    }

}
