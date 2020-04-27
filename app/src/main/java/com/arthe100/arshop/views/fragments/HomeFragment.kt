package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.adapters.ProductAdapter.ProductAdapter
import com.arthe100.arshop.views.data.DataSource
import com.arthe100.arshop.views.data.Product
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class HomeFragment: BaseFragment(), ILoadFragment {

    @Inject lateinit var customArFragment: CustomArFragment
    private lateinit var productAdapter: ProductAdapter

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onStart() {
        setRecyclerView()
        addProducts()
        super.onStart()
    }

    private fun addProducts() {
        val products: List<Product> = DataSource.createDataSet()
        productAdapter.submitList(products)
    }

    private fun setRecyclerView() {
        productAdapter = ProductAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }


    override fun loadFragment(fragment: Fragment?) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment!!, fragment.toString())
                .addToBackStack(fragment.tag)
                .commit()
    }

    override fun toString(): String {
        return "Home"
    }
}
