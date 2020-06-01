package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import retrofit2.http.GET

interface HomeService {
    @GET("events/")
    suspend fun getHomeSales() : List<HomeSales>
}