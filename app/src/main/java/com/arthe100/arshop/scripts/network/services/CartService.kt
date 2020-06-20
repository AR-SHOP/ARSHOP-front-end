package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.AddCart
import com.arthe100.arshop.models.Cart
import com.arthe100.arshop.models.RemoveCart
import retrofit2.http.*

interface CartService {

    @GET("cart/")
    suspend fun getCart() : Cart

    @POST("cart/add/")
    suspend fun add(@Body item: AddCart) : Cart

    @PUT("cart/remove/")
    suspend fun remove(@Body item: RemoveCart) : Cart

    @PATCH("cart/remove/")
    suspend fun decrease(@Body item: AddCart): Cart

    @PATCH("cart/add/")
    suspend fun increase(@Body item: AddCart): Cart

    @DELETE("cart/clear/")
    suspend fun clear(): Cart

}