package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.MyFragmentFactory
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.cart_fragment_layout.*
import javax.inject.Inject


class CartFragment @Inject constructor(
    private val messageManager: MessageManager,
    private val fragmentFactory: MyFragmentFactory
) : BaseFragment() {

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.cart_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter = ViewPagerAdapter(activity?.supportFragmentManager?.fragmentFactory!! ,
            activity?.classLoader!!,
            requireActivity())
        adapter.addFragment(fragmentFactory.instantiate(requireActivity().classLoader , CustomerCartFragment::class.java.name))
        adapter.addFragment(fragmentFactory.instantiate(requireActivity().classLoader , OrdersFragment::class.java.name))
        view_pager.adapter = adapter

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
