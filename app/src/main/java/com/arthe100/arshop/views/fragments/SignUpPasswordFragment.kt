package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.login_fragment_layout.visibility_icon
import kotlinx.android.synthetic.main.sign_up_password_fragment.*
import javax.inject.Inject

class SignUpPasswordFragment : BaseFragment() {

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.bottom_navbar.visibility = View.INVISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up_password_fragment, container, false)
    }


    override fun onStart() {
        setPasswordEditText()
        setRepeatPasswordEditText()
        super.onStart()
    }

    private fun setRepeatPasswordEditText() {
        var passwordVisible = false

        repeat_visibility_icon.setOnClickListener {
            if (passwordVisible) {
                signup_password_repeat.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                repeat_visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
            else {
                signup_password_repeat.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                repeat_visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }

    }

    private fun setPasswordEditText() {
        var passwordVisible = false

        signup_visibility_icon.setOnClickListener {
            if (passwordVisible) {
                signup_password.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                signup_visibility_icon.setColorFilter(resources.getColor(R.color.colorHint, null))
            }
            else {
                signup_password.transformationMethod = SingleLineTransformationMethod.getInstance()
                passwordVisible = true
                signup_visibility_icon.setColorFilter(resources.getColor(R.color.colorPrimary, null))
            }
        }
    }

}
