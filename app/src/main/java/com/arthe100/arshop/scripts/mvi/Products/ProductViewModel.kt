package com.arthe100.arshop.scripts.mvi.Products

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val userSession: UserSession
) : ViewModelBase() , ICacheLoader{
    private val TAG = ProductViewModel::class.simpleName

    var currentProducts: List<Product>? = null

    private lateinit var _product: Product
    var product: Product
        get() = _product
        set(value){
            _product = value
        }


    override fun onEvent(action: UiAction){
        when(action)
        {
            is ProductUiAction.GetProducts -> {

                viewModelScope.launch {
                    _currentViewState.value = productRepo.getProducts()
                    _currentViewState.value = ViewState.IdleState
                }
            }
            is ProductUiAction.GetProductDetailsOffline -> {

                _product = action.product
                _currentViewState.value = ProductState.ProductDetailSuccess(action.product)
                _currentViewState.value = ViewState.IdleState
//                viewModelScope.launch {
//                    val productState = productRepo.getProduct(state.id)
//                    when(productState){
//                        is ProductState.ProductDetailSuccess -> {
//                            _product = productState.product
//                        }
//                    }
//                    _currentViewState.value = productState
//                }

            }
            is ProductUiAction.GetProductDetails -> {
                _product = action.product
//                _currentViewState.value = ViewState.LoadingState

                viewModelScope.launch {
                    val productState = productRepo.getProduct(action.product.id)
                    when(productState){
                        is ProductState.ProductDetailSuccess -> {
                            _product = productState.product
                        }
                    }
                    _currentViewState.value = productState
                }
            }
            is ProductUiAction.SendCommentAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value =
                        productRepo.sendComment(action.comment)

                    _currentViewState.value = ViewState.IdleState
                }
            }
        }
    }

    override fun load(func: (ViewState) -> Unit) {
        if(currentProducts != null)
            func(ProductState.ProductsSuccess(currentProducts!!))
    }

}