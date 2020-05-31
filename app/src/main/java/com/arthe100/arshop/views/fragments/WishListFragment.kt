package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.arthe100.arshop.R
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.interfaces.ILoadFragment
import javax.inject.Inject

class WishListFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
    ) : BaseFragment(), ILoadFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

}
