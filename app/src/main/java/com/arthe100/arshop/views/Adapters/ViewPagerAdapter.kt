package com.arthe100.arshop.views.Adapters

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.fragments.CartFragment
import com.arthe100.arshop.views.fragments.CustomerCartFragment
import com.arthe100.arshop.views.fragments.FragmentFactory
import com.arthe100.arshop.views.fragments.OrdersFragment
import javax.inject.Inject

class ViewPagerAdapter (fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    @Inject lateinit var fragmentFactory : FragmentFactory
    lateinit var customerCartFragment: CustomerCartFragment
    lateinit var ordersFragment: OrdersFragment

    init {
        (fragmentActivity.application as BaseApplication).mainComponent().inject(this)
        customerCartFragment = fragmentFactory.create<CustomerCartFragment>()
        ordersFragment = fragmentFactory.create<OrdersFragment>()
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = Fragment()
        when (position) {
            0 -> {
                fragment = customerCartFragment
            }
            1 -> {
                fragment = ordersFragment
            }
        }
        return fragment
    }
}