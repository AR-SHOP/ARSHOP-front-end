package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.Category
import retrofit2.http.GET

interface CategoryService {

    @GET("categories/")
    suspend fun getCategories() : List<Category>
}