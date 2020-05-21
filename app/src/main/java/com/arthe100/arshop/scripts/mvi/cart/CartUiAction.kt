package com.arthe100.arshop.scripts.mvi.cart

sealed class CartUiAction{
    object GetCart                                              : CartUiAction()
    object ClearCart                                            : CartUiAction()
    object GetCartOnStart                                       : CartUiAction()
    object GetCartInBackground                                  : CartUiAction()
    data class RemoveFromCart(val id: Long)                     : CartUiAction()
    data class AddToCart(val id: Long , val quantity: Int)      : CartUiAction()
    data class DecreaseQuantity(val id: Long , val offset: Int) : CartUiAction()
    data class IncreaseQuantity(val id: Long , val offset: Int) : CartUiAction()
    //data class PlaceOrder() : CartUiAction()
}