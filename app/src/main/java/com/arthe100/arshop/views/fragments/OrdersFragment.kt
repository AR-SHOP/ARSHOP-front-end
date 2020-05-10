package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.Adapters.OnItemClickListener
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CartItemAdapter
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.cart_item.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.orders_fragment.login_btn
import javax.inject.Inject

class OrdersFragment : BaseFragment() {
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession

    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var cartItemAdapter: CartItemAdapter

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create()
        productFragment = fragmentFactory.create()
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        when (session.user) {
            is User.GuestUser -> {
                bottom_buttons.visibility = View.INVISIBLE
                login_btn.visibility = View.VISIBLE
                empty_orders_layout.visibility = View.INVISIBLE
                ordered_items_list.visibility = View.INVISIBLE
                login_btn.setOnClickListener {
                    requireActivity().bottom_navbar.visibility = View.INVISIBLE
                    loginFragment.inMainPage = false
                    loadFragment(loginFragment)
                }
            }
            is User.User -> {
                bottom_buttons.visibility = View.VISIBLE
                login_btn.visibility = View.INVISIBLE
                empty_orders_layout.visibility = View.VISIBLE
                ordered_items_list.visibility = View.VISIBLE
            }
        }
    }

    private fun addProducts(products: List<Product>) {
        cartItemAdapter.submitList(products)
    }

    private fun setRecyclerView() {
        cartItemAdapter = CartItemAdapter()
        cartItemAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
                //productFragment.setProduct(cartItemAdapter.dataList[position])
                loadFragment(productFragment)
            }
        })
        ordered_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
        }
    }


    override fun toString(): String {
        return "Orders Fragment"
    }
}
