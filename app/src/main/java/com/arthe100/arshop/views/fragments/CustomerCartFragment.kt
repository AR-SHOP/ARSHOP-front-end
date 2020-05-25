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
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartState
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.interfaces.OnItemClickListener
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CartItemAdapter
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.customer_cart_fragment_layout.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomerCartFragment @Inject constructor(
    private val session: UserSession,
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val messageManager: MessageManager,
    private val dialogBox: DialogBoxManager
): BaseFragment() {

    lateinit var authViewModel: AuthViewModel
    lateinit var cartItemAdapter: CartItemAdapter
    lateinit var model: CartViewModel
    lateinit var productViewModel: ProductViewModel
    lateinit var customerCartFragmentLayout: ViewGroup


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        authViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        return inflater.inflate(R.layout.customer_cart_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(requireActivity() , Observer(::render))
        //model.currentCart.observe(requireActivity() , Observer{addProducts(it.cartItems)})
        authViewModel.currentViewState.observe(requireActivity() , Observer(::authRender))
    }

    override fun onStart() {
        checkUserLogin()
        super.onStart()
    }

    private fun checkUserLogin() {
        when(session.user) {
            is User.GuestUser -> {
                login_btn?.visibility = View.VISIBLE
                empty_cart_layout?.visibility = View.INVISIBLE
                cart_items_list?.visibility = View.INVISIBLE
                bottom_buttons?.visibility = View.INVISIBLE
                login_btn?.setOnClickListener {
                    loadFragment(LoginFragment::class.java)
                    requireActivity().bottom_navbar.selectedItemId = R.id.btm_navbar_profile
                }
            }
            is User.User -> {
                login_btn?.visibility = View.INVISIBLE
                delete_all_cart_btn?.setOnClickListener{
                    model.onEvent(CartUiAction.ClearCart)
                    delayEnabled(delete_all_cart_btn!!)
                }

                model.onEvent(
                    if(model.currentCart.value == null) CartUiAction.GetCart
                    else CartUiAction.GetCartInBackground
                )
            }
        }
    }

    override fun toString(): String {
        return "Customer Cart"
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
            is CartState.IdleState -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
            }
            is CartState.LoadingState -> {
                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(),MessageType.LOAD)
            }
            is CartState.GetCartState -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                cart_items_list?.visibility = View.VISIBLE
                empty_cart_layout?.visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                if(!this::cartItemAdapter.isInitialized) setRecyclerView(products)
            }
            is CartState.AddToCartState -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
            is CartState.RemoveFromCartState -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
            is CartState.Failure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
                model.updateCart(::addProducts)
            }
            is CartState.ClearCart -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                addProducts(products)
            }
            is CartState.LogoutState -> {
                checkUserLogin()
            }
        }
    }

    private fun uiStatus(cart: Cart){
        empty_cart_layout?.visibility = if(cart.cartItems.isNotEmpty()) View.INVISIBLE else View.VISIBLE
        bottom_buttons?.visibility = if(cart.cartItems.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        cart_total_price?.text = cart.details.totalPrice.toString()
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
            OnItemClickListener<CartItem> {
            override fun onItemClick(position: Int) {
                productViewModel.product = cartItemAdapter.items[position].product
                loadFragment(ProductFragment::class.java)
            }

            override fun onItemClick(data: CartItem) {
                TODO("Not yet implemented")
            }
        })
        cartItemAdapter.plusListener = object :
            OnItemClickListener<CartItem> {
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.IncreaseQuantity(cartItem.product.id, cartItem.quantity))
            }

            override fun onItemClick(data: CartItem) {
                TODO("Not yet implemented")
            }
        }
        cartItemAdapter.minusListener = object:
            OnItemClickListener<CartItem> {
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.DecreaseQuantity(cartItem.product.id , cartItem.quantity))
            }

            override fun onItemClick(data: CartItem) {
                TODO("Not yet implemented")
            }
        }
        cartItemAdapter.deleteListener = object:
            OnItemClickListener<CartItem> {
            override fun onItemClick(position: Int) {
                val cartItem = cartItemAdapter.items[position]
                model.onEvent(CartUiAction.RemoveFromCart(cartItem.product.id))
            }

            override fun onItemClick(data: CartItem) {
                TODO("Not yet implemented")
            }
        }

        cart_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
        }
    }

}
