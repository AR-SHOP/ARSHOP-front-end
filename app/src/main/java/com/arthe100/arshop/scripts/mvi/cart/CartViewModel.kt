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


    var currentCart: Cart? = null

    fun isInCart(id: Long) : Boolean{

        return currentCart?.cartItems
            ?.map { it.product.id }
            ?.contains(id) ?: false
    }

    fun getCartItemById(id: Long) : CartItem?{
        val cart = currentCart
        return cart?.cartItems?.singleOrNull { it.product.id == id }
    }

    private var currentQuantity: Int = -1
    private val delayDuration: Long = 500
    private var currentJob: Job? = null

    fun logout(){
        currentCart = null
        _currentViewState.value = CartState.LogoutState
    }

    fun onEvent(action: CartUiAction)
    {
        when(action){
            CartUiAction.ClearCart -> clearCart()
            CartUiAction.GetCart -> getCart()
            is CartUiAction.GetCartInBackground -> getCart()
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

        if(currentCart != null)
            _currentViewState.value = CartState.GetCartState(currentCart!!)
        else
            _currentViewState.value = CartState.LoadingState

        viewModelScope.launch {
            _currentViewState.value = cartRepo.get()
        }
    }
    private fun getCartOnStart(){
        viewModelScope.launch {

            _currentViewState.value = cartRepo.get()

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