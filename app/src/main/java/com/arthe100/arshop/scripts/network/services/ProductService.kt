package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.Comment
import com.arthe100.arshop.models.CommentNetwork
import com.arthe100.arshop.models.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("products/")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Long): Product

    @POST("create-comment/")
    suspend fun sendComment(@Body comment: CommentNetwork)
}