package com.arthe100.arshop.scripts.mvi.cart

sealed class CartUiAction{
    object ClearCart : CartUiAction()
    object GetCart : CartUiAction()
    data class AddToCart(val id: Long , val quantity: Int) : CartUiAction()
    data class RemoveFromCart(val id: Long) : CartUiAction()
    //data class PlaceOrder() : CartUiAction()
}