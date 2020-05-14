package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartState
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.product_fragment_layout.*
import javax.inject.Inject

class ProductFragment : BaseFragment() {
    @Inject lateinit var customArFragment: CustomArFragment
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var dialogBox: DialogBoxManager

    private lateinit var cartViewModel: CartViewModel
    private lateinit var model: ProductViewModel


    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.product_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model = ViewModelProvider(requireActivity() , viewModelFactory).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelFactory).get(CartViewModel::class.java)
        model.currentViewState.observe(requireActivity() , Observer(::render))
        cartViewModel.currentViewState.observe(requireActivity() , Observer(::render))
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        product_details_name.text = model.product.name
        product_details_brand.text = model.product.manufacturer
        product_details_price.text = model.product.price.toString()
        product_details_description.text = model.product.description
        checkCartStatus()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_empty_background)
            .error(R.drawable.ic_empty_background)

        val cartItem = cartViewModel.getCartItemById(model.product.id)
        cart_count_text?.text = cartItem?.quantity.toString()

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(model.product.thumbnail)
            .into(product_details_image)

        ar_btn?.setOnClickListener {
            customArFragment.setUri(model.product.arModel)
            loadFragment(customArFragment)
        }
        add_to_cart_btn?.setOnClickListener {
            cartViewModel.onEvent(CartUiAction.AddToCart(model.product.id , 1))
            cart_count_text?.text = 1.toString()
            add_to_cart_btn?.visibility = View.INVISIBLE
            inc_dec_cart_count?.visibility = View.VISIBLE
        }
        super.onStart()
    }

    override fun toString(): String {
        return "Product"
    }

    private fun render(state: ProductState){
        when(state){
            ProductState.Idle -> {
                dialogBox.cancel()
                product_fragment_layout.visibility = View.VISIBLE
            }

            ProductState.LoadingState -> {
                product_fragment_layout.visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }

            is ProductState.ProductDetailSuccess -> {
                dialogBox.cancel()
                product_fragment_layout.visibility = View.VISIBLE
            }

            is ProductState.GetProductsFailure -> {
                product_fragment_layout.visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
                Log.v("TAG", state.throwable.toString())
            }
        }
    }

    private fun render(state: CartState){
        when(state){
            is CartState.AddToCartState -> {
                checkCartStatus()
            }
        }
    }



    private fun checkCartStatus(){

        plus_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id)!!
            val newQuantity = (cart_count_text?.text.toString().toInt() + 1).coerceIn(0..Int.MAX_VALUE)
            cartItem.quantity = newQuantity
            cart_count_text?.text = newQuantity.toString()
            cartViewModel.onEvent(CartUiAction.IncreaseQuantity(cartItem.product.id, cartItem.quantity))
            cartViewModel.updateCart()
        }
        minus_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id)!!
            val newQuantity = (cart_count_text?.text.toString().toInt() - 1).coerceIn(0..Int.MAX_VALUE)
            cartItem.quantity = newQuantity
            cart_count_text?.text = newQuantity.toString()
            cartViewModel.onEvent(CartUiAction.DecreaseQuantity(cartItem.product.id , cartItem.quantity))
            cartViewModel.updateCart()
        }
        delete_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id)!!
            cartItem.quantity = 0
            cartViewModel.onEvent(CartUiAction.RemoveFromCart(cartItem.product.id))
            add_to_cart_btn?.visibility = View.VISIBLE
            inc_dec_cart_count?.visibility = View.INVISIBLE
            cartViewModel.updateCart()
        }

        if(cartViewModel.isInCart(model.product.id) )
        {
            add_to_cart_btn?.visibility = View.INVISIBLE
            inc_dec_cart_count?.visibility = View.VISIBLE
        }
        else{
            add_to_cart_btn?.visibility = View.VISIBLE
            inc_dec_cart_count?.visibility = View.INVISIBLE

        }
    }

}
