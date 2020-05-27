package com.arthe100.arshop.scripts.mvi.Products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.repositories.ProductRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepo: ProductRepository) : ViewModel(){
    private val TAG = ProductViewModel::class.simpleName
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

    fun loadFromCache(func: (ProductState) -> Unit){
        if(currentSales != null)
            func(ProductState.HomePageSalesSuccess(currentSales!!))
        if(currentProducts != null)
            func(ProductState.GetProductsSuccess(currentProducts!!))
    }

    fun onEvent(state: ProductUiAction){
        when(state)
        {
            is ProductUiAction.GetHomePageProducts -> {

                // load from cache first
//                if(currentProducts != null){
//                    _currentViewState.value = ProductState.GetProductsSuccess(currentProducts!!)
//                    Log.d(TAG , "products")
//                }
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

//                // load from cache first
//                if(currentSales != null){
//                    _currentViewState.value = ProductState.HomePageSalesSuccess(currentSales!!)
//                    Log.d(TAG , "sales")
//                }
                viewModelScope.launch {
                    _currentViewState.value = productRepo.getHomeSales()
                    _currentViewState.value = ProductState.Idle
                }
            }
        }
    }

}