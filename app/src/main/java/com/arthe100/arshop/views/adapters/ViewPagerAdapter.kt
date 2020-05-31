package com.arthe100.arshop.views.adapters

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.arthe100.arshop.views.fragments.CustomerCartFragment
import com.arthe100.arshop.views.fragments.OrdersFragment

class ViewPagerAdapter (private val fragmentFactory: FragmentFactory ,
                        private val classLoader: ClassLoader,
                        fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    val fragments = mutableListOf<Fragment>()
    var currentFragment: Int = -1


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }


    fun addFragment(fragment: Fragment) = fragments.add(fragment)


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        currentFragment = position
        return fragments[position]
    }
}