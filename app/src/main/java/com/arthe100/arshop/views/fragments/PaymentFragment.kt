package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.interfaces.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import javax.inject.Inject

class PaymentFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory)
    : BaseFragment(), ILoadFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar?.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun render(state: ViewState) {
        TODO("Not yet implemented")
    }

}
