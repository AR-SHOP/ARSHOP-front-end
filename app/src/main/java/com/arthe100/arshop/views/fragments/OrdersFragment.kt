package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.Adapters.OnItemClickListener
import com.arthe100.arshop.views.Adapters.ProductAdapter
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.customer_cart_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.orders_fragment.login_btn
import javax.inject.Inject

class OrdersFragment : BaseFragment() {

    @Inject lateinit var fragmentFactory: FragmentFactory
    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var productAdapter: ProductAdapter
    var loggedIn: Boolean = false

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create<LoginFragment>()
        productFragment = fragmentFactory.create<ProductFragment>()
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        if (loggedIn) {
            login_btn.visibility = View.INVISIBLE
            empty_orders_layout.visibility = View.VISIBLE
            ordered_items_list.visibility = View.VISIBLE

        }
        else {
            login_btn.visibility = View.VISIBLE
            empty_orders_layout.visibility = View.INVISIBLE
            ordered_items_list.visibility = View.INVISIBLE
            login_btn.setOnClickListener {
                requireActivity().bottom_navbar.visibility = View.INVISIBLE
                loginFragment.inCartFragment = true
                loadFragment(loginFragment)
            }
        }
    }

    private fun addProducts(products: List<Product>) {
        productAdapter.submitList(products)
    }

    private fun setRecyclerView() {
        productAdapter = ProductAdapter()
        productAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
                productFragment.setProduct(productAdapter.dataList[position])
                loadFragment(productFragment)
            }
        })
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }


    override fun toString(): String {
        return "Orders Fragment"
    }
}
