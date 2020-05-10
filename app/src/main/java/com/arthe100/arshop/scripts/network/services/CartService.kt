package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.RemoveCart
import retrofit2.http.*

interface CartService {

    @GET("cart/")
    suspend fun getCart() : Cart

    @POST("add-to-cart/")
    suspend fun add(@Body item: AddCart) : Cart

    @PUT("remove-from-cart/")
    suspend fun remove(@Body item: RemoveCart) : Cart

    @PATCH("remove-from-cart/")
    suspend fun decrease(@Body item: AddCart): Cart

    @PATCH("add-to-cart/")
    suspend fun increase(@Body item: AddCart): Cart

    @DELETE("clear-cart/")
    suspend fun clear(): Cart

}