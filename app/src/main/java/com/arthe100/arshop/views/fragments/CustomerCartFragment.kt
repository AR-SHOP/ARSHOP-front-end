package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartState
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.Adapters.CartItemAdapter
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.customer_cart_fragment_layout.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomerCartFragment : BaseFragment() {
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var session: UserSession
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager

    lateinit var authViewModel: AuthViewModel
    lateinit var loginFragment: LoginFragment
    lateinit var productFragment: ProductFragment
    lateinit var cartItemAdapter: CartItemAdapter
    lateinit var model: CartViewModel
    lateinit var productViewModel: ProductViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        loginFragment = fragmentFactory.create<LoginFragment>()
        productFragment = fragmentFactory.create<ProductFragment>()
        authViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        model.currentViewState.observe(requireActivity() , Observer(::render))
        //model.currentCart.observe(requireActivity() , Observer{addProducts(it.cartItems)})
        authViewModel.currentViewState.observe(requireActivity() , Observer(::authRender))
        return inflater.inflate(R.layout.customer_cart_fragment_layout, container, false)
    }

    override fun onStart() {
        when(session.user) {
            is User.GuestUser -> {
                login_btn?.visibility = View.VISIBLE
                empty_cart_layout?.visibility = View.INVISIBLE
                cart_items_list?.visibility = View.INVISIBLE
                bottom_buttons?.visibility = View.INVISIBLE
                login_btn?.setOnClickListener {
                    loadFragment(loginFragment)
                    requireActivity().bottom_navbar.selectedItemId = R.id.btm_navbar_profile
                }
            }
            is User.User -> {
                login_btn?.visibility = View.INVISIBLE
                delete_all_cart_btn?.setOnClickListener{
                    model.onEvent(CartUiAction.ClearCart)
                    delayEnabled(delete_all_cart_btn!!)
                }
                model.onEvent(CartUiAction.GetCart)
            }
        }
        super.onStart()
    }

    private fun delayEnabled(btn: Button){
        btn.isEnabled = false
        lifecycleScope.launch {
            delay(500)
            btn.isEnabled = true
        }
    }

    private fun authRender(state: AuthState) {

        when (state) {
            is AuthState.LoginSuccess -> {
                login_btn?.visibility = View.INVISIBLE
                cart_items_list?.visibility = View.VISIBLE
                delete_all_cart_btn?.setOnClickListener{
                    model.onEvent(CartUiAction.ClearCart)
                }
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
                loading_bar?.visibility = View.INVISIBLE
                cart_items_list?.visibility = View.VISIBLE
                empty_cart_layout?.visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                setRecyclerView(products)
            }
            is CartState.AddToCartState -> {
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
            is CartState.RemoveFromCartState -> {
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
            is CartState.Failure -> {
                loading_bar?.visibility = View.INVISIBLE
                messageManager.toast(requireContext() , state.err.toString())
                model.updateCart(::addProducts)
            }
            is CartState.ClearCart -> {
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
        }
    }

    private fun uiStatus(cart: Cart){
        empty_cart_layout?.visibility = if(cart.cartItems.isNotEmpty()) View.INVISIBLE else View.VISIBLE
        bottom_buttons?.visibility = if(cart.cartItems.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        cart_total_price?.text = cart.details.totalPrice.toString()
    }


    override fun toString(): String {
        return "Customer Cart Fragment"
    }

    private fun addProducts(cartItems: List<CartItem>) {
        if(!this::cartItemAdapter.isInitialized)return
        val newList = mutableListOf<CartItem>()
        newList.addAll(cartItems)
        cartItemAdapter.submitList(newList)
    }

    private fun setRecyclerView(cartItems: List<CartItem>) {
        cartItemAdapter = CartItemAdapter(cartItems)
        cartItemAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
                productViewModel.product = cartItemAdapter.items[position].product
                loadFragment(productFragment)
            }
        })
        cartItemAdapter.plusListener = object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.IncreaseQuantity(cartItem.product.id, cartItem.quantity))
            }
        }
        cartItemAdapter.minusListener = object: OnItemClickListener{
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.DecreaseQuantity(cartItem.product.id , cartItem.quantity))
            }
        }
        cartItemAdapter.deleteListener = object: OnItemClickListener{
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.RemoveFromCart(cartItem.product.id))
            }
        }

        cart_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
        }
    }

}
