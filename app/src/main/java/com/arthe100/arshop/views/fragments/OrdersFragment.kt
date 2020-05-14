package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CartItemAdapter
import com.arthe100.arshop.views.adapters.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.orders_fragment.login_btn
import javax.inject.Inject

class OrdersFragment : BaseFragment() {
    @Inject lateinit var fragmentFactory: FragmentFactory
    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var cartItemAdapter: CartItemAdapter
    var loggedIn: Boolean = false

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create()
        productFragment = fragmentFactory.create()
        requireActivity().bottom_navbar.visibility = View.VISIBLE
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
                loadFragment(loginFragment)
            }
        }
    }

    private fun addProducts(products: MutableList<CartItem>) {
        cartItemAdapter.submitList(products)
    }

    override fun toString(): String {
        return "Orders"
    }

    private fun authRender(state: AuthState) {
        when (state) {
            is AuthState.LoginSuccess -> {
                bottom_buttons.visibility = View.VISIBLE
                login_btn.visibility = View.INVISIBLE
                empty_orders_layout.visibility = View.VISIBLE
                ordered_items_list.visibility = View.INVISIBLE
            }
        }
    }



    private fun setRecyclerView() {
//        cartItemAdapter = CartItemAdapter()
        cartItemAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
//                productFragment.setProduct(cartItemAdapter.dataList[position])
//                loadFragment(productFragment)
            }
        })
        ordered_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
        }
    }


}
