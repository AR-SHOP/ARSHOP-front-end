package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.RemoveCart
import com.arthe100.arshop.scripts.mvi.cart.CartState
import com.arthe100.arshop.scripts.network.services.CartService
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartService: CartService) {

    suspend fun get() : CartState{
        return try {
            val cart = cartService.getCart()
            CartState.GetCartState(cart)
        }catch (err: Throwable)
        {
            CartState.Failure(err)
        }
    }
    suspend fun add(item : AddCart) : CartState{
        return try {
            val cart = cartService.add(item)
            CartState.AddToCartState(cart)
        }catch (err: Throwable)
        {
            CartState.Failure(err)
        }
    }
    suspend fun remove(item: RemoveCart) : CartState{
        return try {
            val cart = cartService.remove(item)
            CartState.AddToCartState(cart)
        }catch (err: Throwable)
        {
            CartState.Failure(err)
        }
    }

}