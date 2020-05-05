package com.arthe100.arshop.scripts.mvi.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.repositories.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepo: ProductRepository) : ViewModel(){
    private val _currentViewState = MutableLiveData<ProductState>()
    val currentViewState : LiveData<ProductState>
        get() = _currentViewState

    init {
        _currentViewState.value = ProductState.Idle
    }

    fun onEvent(state: ProductUiAction){
        when(state)
        {
            is ProductUiAction.GetHomePageProducts -> {
                viewModelScope.launch {
                    _currentViewState.value = productRepo.getProducts()
                }
            }
        }
    }
}