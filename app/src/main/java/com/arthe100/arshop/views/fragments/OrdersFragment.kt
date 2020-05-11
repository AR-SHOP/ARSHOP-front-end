package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CartItemAdapter
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.customer_cart_fragment_layout.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.orders_fragment.bottom_buttons
import kotlinx.android.synthetic.main.orders_fragment.login_btn
import javax.inject.Inject

class OrdersFragment : BaseFragment() {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession

    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var cartItemAdapter: CartItemAdapter
    lateinit var authViewModel: AuthViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create()
        productFragment = fragmentFactory.create()
        authViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        authViewModel.currentViewState.observe(requireActivity() , Observer(::authRender))
        return inflater.inflate(R.layout.orders_fragment, container, false)
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



    override fun onStart() {
        super.onStart()

        when (session.user) {
            is User.GuestUser -> {
                bottom_buttons.visibility = View.INVISIBLE
                login_btn.visibility = View.VISIBLE
                empty_orders_layout.visibility = View.INVISIBLE
                ordered_items_list.visibility = View.INVISIBLE
                login_btn.setOnClickListener {
                    loadFragment(loginFragment)
                    requireActivity().bottom_navbar.selectedItemId = R.id.btm_navbar_profile
                }
            }
            is User.User -> {
                bottom_buttons.visibility = View.VISIBLE
                login_btn.visibility = View.INVISIBLE
                empty_orders_layout.visibility = View.VISIBLE
                ordered_items_list.visibility = View.INVISIBLE
            }
        }
    }

//    private fun addProducts(products: List<CartItem>) {
//        cartItemAdapter.submitList(products)
//    }

    private fun setRecyclerView(items: List<CartItem>) {
        cartItemAdapter = CartItemAdapter(items)
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
