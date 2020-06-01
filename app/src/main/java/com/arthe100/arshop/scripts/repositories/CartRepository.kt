package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.RemoveCart
import com.arthe100.arshop.scripts.mvi.base.CartState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.CartService
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartService: CartService) {

    suspend fun get() : ViewState {
        return try {
            val cart = cartService.getCart()
            CartState.GetCartState(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }
    suspend fun add(item : AddCart) : ViewState{
        return try {
            val cart = cartService.add(item)
            CartState.AddToCartState(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }
    suspend fun remove(item: RemoveCart) : ViewState{
        return try {
            val cart = cartService.remove(item)
            CartState.RemoveFromCartState(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }

    suspend fun decrease(item: AddCart): ViewState {
        return try {
            val cart = cartService.decrease(item)
            CartState.AddToCartState(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }

    suspend fun increase(item: AddCart): ViewState {
        return try {
            val cart = cartService.increase(item)
            CartState.AddToCartState(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }

    suspend fun clear(): ViewState {
        return try {
            val cart = cartService.clear()
            CartState.ClearCart(cart)
        }catch (err: Throwable)
        {
            ViewState.Failure(err)
        }
    }

}