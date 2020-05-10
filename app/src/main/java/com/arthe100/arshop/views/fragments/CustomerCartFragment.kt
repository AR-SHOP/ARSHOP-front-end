package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.cart.CartState
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.Adapters.OnItemClickListener
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CartItemAdapter
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.customer_cart_fragment_layout.*
import javax.inject.Inject

class CustomerCartFragment : BaseFragment() {
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager


    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var cartItemAdapter: CartItemAdapter
    lateinit var model: CartViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create<LoginFragment>()
        productFragment = fragmentFactory.create<ProductFragment>()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        model.currentViewState.observe(requireActivity() , Observer(::render))
        return inflater.inflate(R.layout.customer_cart_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        when(session.user){
            is User.GuestUser -> {
                login_btn.visibility = View.VISIBLE
                empty_cart_layout.visibility = View.INVISIBLE
                cart_items_list.visibility = View.INVISIBLE
                bottom_buttons.visibility = View.INVISIBLE
                login_btn.setOnClickListener {
                    requireActivity().bottom_navbar.visibility = View.INVISIBLE
                    loginFragment.inMainPage = false
                    loadFragment(loginFragment)
                }
            }
            is User.User -> {
                bottom_buttons.visibility = View.VISIBLE
                login_btn.visibility = View.INVISIBLE
                empty_cart_layout.visibility = View.VISIBLE
                cart_items_list.visibility = View.VISIBLE
                model.onEvent(CartUiAction.GetCart)
            }
        }

    }



    private fun render(state: CartState){
        when(state) {
            CartState.IdleState -> {
                loading_bar?.visibility = View.INVISIBLE
            }
            CartState.LoadingState -> {
                loading_bar?.visibility = View.VISIBLE
            }
            is CartState.GetCartState -> {
                val products = state.cart.cartItems.map { it.product }
                setRecyclerView()
                addProducts(products)
            }
//            is CartState.AddToCartState -> TODO()
//            is CartState.RemoveFromCartState -> TODO()
            is CartState.Failure -> {
                loading_bar?.visibility = View.INVISIBLE
                messageManager.toast(requireContext() , state.err.toString())
            }
        }
    }


    override fun toString(): String {
        return "Customer Cart Fragment"
    }

    private fun addProducts(products: List<Product>) {
        cartItemAdapter.submitList(products)
    }

    private fun setRecyclerView() {
        cartItemAdapter = CartItemAdapter()
        cartItemAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
//                productFragment.setProduct(cartItemAdapter.dataList[position])
                loadFragment(productFragment)
            }
        })
        cart_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
        }
    }

}
