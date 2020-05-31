package com.arthe100.arshop.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
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
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.*
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
    lateinit var cartItemAdapter: GenericAdapter<CartItem>
    lateinit var model: CartViewModel
    lateinit var productViewModel: ProductViewModel
    lateinit var customerCartFragmentLayout: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


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
                    if(model.currentCart == null) CartUiAction.GetCart
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
//                requireView().visibility = View.VISIBLE
            }
            is CartState.LoadingState -> {
                requireView().visibility = View.INVISIBLE
//                dialogBox.showDialog(requireActivity(),MessageType.LOAD)
            }
            is CartState.GetCartState -> {
                model.currentCart = state.cart
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                cart_items_list?.visibility = View.VISIBLE
                empty_cart_layout?.visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                if(!this::cartItemAdapter.isInitialized) setRecyclerView(products)
            }
            is CartState.AddToCartState -> {
                model.currentCart = state.cart
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
//                setRecyclerView(products)
            }
            is CartState.RemoveFromCartState -> {
                model.currentCart = state.cart
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
//                setRecyclerView(products)
            }
            is CartState.Failure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")

                if(model.currentCart != null)
                    setRecyclerView(model.currentCart?.cartItems!!)
            }
            is CartState.ClearCart -> {
                model.currentCart = state.cart
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val products = state.cart.cartItems
                uiStatus(state.cart)
                setRecyclerView(products)
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



    private fun setRecyclerView(cartItems: List<CartItem>) {

        if(this::cartItemAdapter.isInitialized){
            cart_items_list.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = cartItemAdapter
            }
            cartItemAdapter.addItems(cartItems)
            return
        }

        cartItemAdapter =object: GenericAdapter<CartItem>(){
            override fun getLayoutId(position: Int, obj: CartItem): Int = R.layout.cart_item
        }

        cartItemAdapter.setItemListener(object :
            OnItemClickListener<CartItem> {
            override fun onClickItem(data: CartItem) {
                productViewModel.product = data.product

                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .detach(this@CustomerCartFragment)
                    .commit()

                loadFragment(ProductFragment::class.java)
            }
        })

        cartItemAdapter.setViewListeners(listOf(
            ViewListeners(R.id.plus_btn , object: OnItemClickListenerForView<CartItem> {
                override fun onClickItem(data: CartItem, position: Int) {
                    val txtQuantity = cart_items_list.layoutManager?.findViewByPosition(position)?.findViewById<TextView>(R.id.cart_count_text)
                    val newQuantity = (txtQuantity?.text.toString().toInt() + 1).coerceIn(0..Int.MAX_VALUE)
                    data.quantity
                    val newItem = data.copy(
                        quantity = newQuantity
                    )
                    cartItemAdapter.changeItem(newItem, position)
                    model.onEvent(CartUiAction.IncreaseQuantity(data.product.id, newQuantity))} }),
            ViewListeners(R.id.minus_btn , object: OnItemClickListenerForView<CartItem> {
                override fun onClickItem(data: CartItem, position: Int) {
                    val txtQuantity = cart_items_list.layoutManager?.findViewByPosition(position)?.findViewById<TextView>(R.id.cart_count_text)
                    val newQuantity = (txtQuantity?.text.toString().toInt() - 1).coerceIn(0..Int.MAX_VALUE)
                    data.quantity
                    val newItem = data.copy(
                        quantity = newQuantity
                    )
                    if (newQuantity > 0) cartItemAdapter.changeItem(newItem, position)
                    else cartItemAdapter.removeItem(position)
                    model.onEvent(CartUiAction.DecreaseQuantity(data.product.id , newQuantity))} }),
            ViewListeners(R.id.delete_btn , object: OnItemClickListenerForView<CartItem> {
                override fun onClickItem(data: CartItem, position: Int) {
                    cartItemAdapter.removeItem(position)
                    model.onEvent(CartUiAction.RemoveFromCart(data.product.id)) }})
        ))

        cartItemAdapter.setDiffUtil(object: GenericItemDiff<CartItem>{
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
                oldItem.product.id == newItem.product.id

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
                oldItem.quantity == newItem.quantity
        })

        val animator = DefaultItemAnimator()
        animator.apply {
            addDuration = 500
        }

        cart_items_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartItemAdapter
            itemAnimator =animator
        }

        cartItemAdapter.addItems(cartItems)
    }

}
