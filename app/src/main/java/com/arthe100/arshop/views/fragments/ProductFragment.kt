package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

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

    private lateinit var cartViewModel: CartViewModel
    private lateinit var model: ProductViewModel


    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelFactory).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelFactory).get(CartViewModel::class.java)

        model.currentViewState.observe(requireActivity() , Observer(::render))
        cartViewModel.currentViewState.observe(requireActivity() , Observer(::render))
        return inflater.inflate(R.layout.product_fragment_layout, container, false)
    }

    private fun render(state: ProductState){
        when(state){
            ProductState.Idle -> {
                loading_bar.visibility = View.INVISIBLE
            }
            ProductState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE
            }
            is ProductState.ProductDetailSuccess -> {

            }
            is ProductState.GetProductsFailure -> {
                loading_bar.visibility = View.INVISIBLE
                messageManager.toast(requireContext() , state.throwable.toString())
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

    override fun onStart() {
        product_details_name.text = model.product.name
        product_details_brand.text = model.product.manufacturer
        product_details_price.text = model.product.price.toString()
        product_details_description.text = model.product.description
        checkCartStatus()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(model.product.thumbnail)
            .into(product_details_image)

        ar_btn.setOnClickListener {
            customArFragment.setUri(model.product.arModel)
            loadFragment(customArFragment)
        }
        add_to_cart_btn.setOnClickListener {
            cartViewModel.onEvent(CartUiAction.AddToCart(model.product.id , 1))
        }
        super.onStart()
    }



    fun checkCartStatus(){
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

    override fun toString(): String {
        return "Product Fragment"
    }

}
