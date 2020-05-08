package com.arthe100.arshop.scripts.mvi.cart

import com.arthe100.arshop.models.Cart

sealed class CartState{
    object IdleState : CartState()
    object LoadingState : CartState()
    data class GetCartState(val cart: Cart) : CartState()
    data class AddToCartState(val cart: Cart) : CartState()
    data class RemoveFromCartState(val cart: Cart) : CartState()
    data class Failure(val err: Throwable) : CartState()
//    object PlaceOrderState : CartState()
}