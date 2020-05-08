package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.RemoveCart
import retrofit2.http.*

interface CartService {

    @GET("cart/")
    suspend fun getCart() : Cart

    @PUT("add-to-cart/")
    suspend fun add(@Body item: AddCart) : Cart

    @PUT("remove-from-cart/")
    suspend fun remove(@Body item: RemoveCart) : Cart

}