package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.Product
import retrofit2.http.GET

interface ProductService
{
    @GET("products/")
    suspend fun getProduct() : List<Product>
}