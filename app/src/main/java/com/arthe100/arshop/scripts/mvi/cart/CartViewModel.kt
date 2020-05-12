package com.arthe100.arshop.scripts.mvi.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.models.RemoveCart
import com.arthe100.arshop.scripts.repositories.CartRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartRepo: CartRepository) : ViewModel(){

    private val _currentViewState =  MutableLiveData<CartState>(CartState.IdleState)
    val currentViewState : LiveData<CartState>
        get() = _currentViewState

    private val _currentCart = MutableLiveData<Cart>()
    val currentCart: LiveData<Cart>
        get() = _currentCart


    fun isInCart(id: Long) : Boolean{
        val cart = _currentCart.value

        return cart?.cartItems
            ?.map { it.product.id }
            ?.contains(id) ?: false
    }

    fun getCartItemById(id: Long) : CartItem?{
        val cart = currentCart.value
        return cart?.cartItems?.singleOrNull { it.product.id == id }
    }

    fun updateCart(){
        _currentCart.value = _currentCart.value
    }
    fun updateCart(updateAdapterFun: (cartItems: List<CartItem>) -> Unit){
        _currentCart.value = _currentCart.value
        updateAdapterFun(_currentCart.value!!.cartItems)
    }

    private var currentQuantity: Int = -1
    private val delayDuration: Long = 500
    private var currentJob: Job? = null

    fun onEvent(action: CartUiAction)
    {
        when(action){
            CartUiAction.ClearCart -> clearCart()
            CartUiAction.GetCart -> getCart()
            CartUiAction.GetCartOnStart -> getCartOnStart()
            is CartUiAction.AddToCart -> add(action.id , action.quantity)
            is CartUiAction.IncreaseQuantity -> increase(action.id , action.offset)
            is CartUiAction.DecreaseQuantity -> decrease(action.id , action.offset)
            is CartUiAction.RemoveFromCart -> remove(action.id)
        }
    }

    private fun clearCart() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _currentViewState.value = cartRepo.clear()
        }
    }


    private fun getCart(){

        _currentViewState.value = CartState.LoadingState

        viewModelScope.launch {
            val state = cartRepo.get()
            when(state){
                is CartState.GetCartState -> {
                    _currentCart.value = state.cart
                }
            }
            _currentViewState.value = state
        }
    }
    private fun getCartOnStart(){
        viewModelScope.launch {

            val state = cartRepo.get()
            when(state){
                is CartState.GetCartState -> {
                    _currentCart.value = state.cart
                }
            }
        }
    }


    private suspend fun startTimer(condition: () -> Boolean, request:suspend () -> Unit){

        delay(delayDuration)
        if(condition())
            request()
    }


    private fun decrease(id : Long , quantity: Int){
        currentQuantity = quantity
        currentJob?.cancel()

        if(quantity == 0){
            remove(id)
            return
        }

        currentJob = viewModelScope.launch {
            startTimer({ currentQuantity == quantity} ,
                {
                    Log.d("abcd" , "decreased: $currentQuantity")
                    _currentViewState.value = cartRepo.decrease(
                        AddCart(
                            id = id,
                            quantity = quantity))
                }
            )
        }
    }

    private fun increase(id: Long, quantity: Int) {
        currentQuantity = quantity
        currentJob?.cancel()
        currentJob = viewModelScope.launch {

            startTimer({ currentQuantity == quantity },
                {
                    Log.d("abcd", "increased $currentQuantity")
                    _currentViewState.value = cartRepo.increase(
                        AddCart(
                            id = id,
                            quantity = quantity))
                }
            )
        }
    }

    private fun add(id : Long , quantity: Int){
        viewModelScope.launch {
            val state = cartRepo.add(
                AddCart(
                    id = id,
                    quantity = quantity
                )
            )
            when(state){
                is CartState.AddToCartState -> {
                    _currentCart.value = state.cart
                }
            }
            _currentViewState.value = state
        }
    }
    private fun remove(id : Long ){
        viewModelScope.launch {
            val state = cartRepo.remove(
                RemoveCart(
                    id = id
                )
            )
            when(state){
                is CartState.RemoveFromCartState -> {
                    _currentCart.value = state.cart
                }
            }
            _currentViewState.value = state
        }
    }

}