package com.arthe100.arshop.scripts.network.api

import com.arthe100.arshop.models.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NormalProductApi
{
    @GET("NormalProduct")
    fun getNormalProduct(
        @Query("Name") name: String,
        @Query("Description") description: String,
        @Query("Manufacturer") manufacturer: String,
        @Query("Price") price: Long
    ): Call<List<Product.NormalProduct>>

    @POST("NormalProduct")
    fun addNormalProduct(
        @Body normalProduct: Product.NormalProduct
    ): Call<Product.NormalProduct>

    @PUT("NormalProduct/{Name}")
    fun putName(
        @Path("Name") name: String
    ): Call<Product.NormalProduct>

    @PUT("NormalProduct/{Description}")
    fun putDescription(
        @Path("Description") description: String
    ): Call<Product.NormalProduct>

    @PUT("NormalProduct/{Manufacturer}")
    fun putManufacturer(
        @Path("Manufacturer") manufacturer: String
    ): Call<Product.NormalProduct>

    @PUT("NormalProduct/{Price}")
    fun putPrice(
        @Path("Price") price: Long
    ): Call<Product.NormalProduct>
}