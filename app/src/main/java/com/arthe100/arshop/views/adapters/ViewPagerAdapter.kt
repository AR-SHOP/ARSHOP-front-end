package com.arthe100.arshop.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arthe100.arshop.views.fragments.CustomerCartFragment
import com.arthe100.arshop.views.fragments.OrdersFragment

class ViewPagerAdapter (private val fragmentFactory: FragmentFactory ,
                        private val classLoader: ClassLoader,
                        fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentFactory.instantiate(classLoader , CustomerCartFragment::class.java.name)
            1 -> fragmentFactory.instantiate(classLoader , OrdersFragment::class.java.name)
            else -> throw IllegalArgumentException("Invalid state for viewPager!")
        }
    }
}