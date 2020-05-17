package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    @GET("categories/")
    suspend fun getCategories() : List<Category>

    @GET("category-list/{id}")
    suspend fun getProducts(@Path("id") id: Long): List<Product>
}