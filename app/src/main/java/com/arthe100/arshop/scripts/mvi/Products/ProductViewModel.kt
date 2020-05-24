package com.arthe100.arshop.scripts.mvi.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.repositories.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepo: ProductRepository) : ViewModel(){
    private val _currentViewState = MutableLiveData<ProductState>(ProductState.Idle)
    val currentViewState : LiveData<ProductState>
        get() = _currentViewState

    var currentProducts: List<Product>? = null
    var currentSales: List<HomeSales>? = null


    private lateinit var _product: Product
    var product: Product
        get() = _product
        set(value){
            _product = value
        }

    fun onEvent(state: ProductUiAction){
        when(state)
        {
            is ProductUiAction.GetHomePageProducts -> {

                // load from cache first
                if(currentProducts != null)
                    _currentViewState.value = ProductState.GetProductsSuccess(currentProducts!!)

                viewModelScope.launch {
                    _currentViewState.value = productRepo.getProducts()
                    _currentViewState.value = ProductState.Idle
                }
            }
            is ProductUiAction.GetProductDetails -> {

                _product = state.product
                _currentViewState.value = ProductState.ProductDetailSuccess(state.product)
                _currentViewState.value = ProductState.Idle
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
            is ProductUiAction.GetHomePageSales -> {

                // load from cache first
                if(currentSales != null)
                    _currentViewState.value = ProductState.HomePageSalesSuccess(currentSales!!)

                viewModelScope.launch {
                    _currentViewState.value = productRepo.getHomeSales()
                    _currentViewState.value = ProductState.Idle
                }
            }
        }
    }

}