package com.arthe100.arshop.scripts.mvi.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.RemoveCart
import com.arthe100.arshop.scripts.repositories.CartRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartRepo: CartRepository) : ViewModel(){

    private val _currentViewState =  MutableLiveData<CartState>(CartState.IdleState)
    val currentViewState : LiveData<CartState>
        get() = _currentViewState

    private lateinit var currentCart: Cart

    fun isInCart(id: Long) : Boolean{
        if(!this::currentCart.isInitialized) return false
        return currentCart.cartItems
            .map { it.product.id }
            .contains(id)
    }

    fun onEvent(action: CartUiAction)
    {
        when(action){
            CartUiAction.ClearCart -> TODO()
            CartUiAction.GetCart -> getCart()
            is CartUiAction.AddToCart -> add(action.id , action.quantity)
            is CartUiAction.RemoveFromCart -> remove(action.id)
        }
    }

    private fun getCart(){
        viewModelScope.launch {

            val state = cartRepo.get()
            when(state){
                is CartState.GetCartState -> {
                    currentCart = state.cart
                }
            }
            _currentViewState.value = state
        }
    }

    private fun add(id : Long , quantity: Int){
        viewModelScope.launch {
            _currentViewState.value = cartRepo.add(
                AddCart(
                    id = id,
                    quantity = quantity
                )
            )
        }
    }
    private fun remove(id : Long ){
        viewModelScope.launch {
            _currentViewState.value = cartRepo.remove(
                RemoveCart(
                    id = id
                )
            )
        }
    }

}