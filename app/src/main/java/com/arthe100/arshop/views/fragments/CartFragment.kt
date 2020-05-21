package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.adapters.ViewPagerAdapter
import com.arthe100.arshop.views.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.cart_fragment_layout.*
import javax.inject.Inject


class CartFragment @Inject constructor(
    private val messageManager: MessageManager
) : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.cart_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()

        val viewPagerAdapter = ViewPagerAdapter(activity?.supportFragmentManager?.fragmentFactory!! ,
            activity?.classLoader!!,
            requireActivity())
        view_pager.adapter = viewPagerAdapter

        val tabLayoutMediator =
            TabLayoutMediator(tab_layout, view_pager,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    when (position) {
                        0 -> {
                            tab.text = "سبد خرید"
                        }
                        1 -> {
                            tab.text = "سفارش\u200Cها"
                        }
                    }
                })
        tabLayoutMediator.attach()
    }


    override fun toString(): String {
        return "Cart"
    }

}
