package com.arthe100.arshop.scripts.network.api

import com.arthe100.arshop.models.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ArProductApi
{
    @GET("ArProduct")
    fun getArProduct(
        @Query("Name") name: String,
        @Query("Description") description: String,
        @Query("Manufacturer") manufacturer: String,
        @Query("Price") price: Long,
        @Query("Image") url: String
    ): Call<List<Product.ArProduct>>

    @POST("ArProduct")
    fun addArProduct(
        @Body arProduct: Product.ArProduct
    ): Call<Product.ArProduct>

    @PUT("ArProduct/{Name}")
    fun putName(
        @Path("Name") name: String
    ): Call<Product.ArProduct>

    @PUT("ArProduct/{Description}")
    fun putDescription(
        @Path("Description") description: String
    ): Call<Product.ArProduct>

    @PUT("ArProduct/{Manufacturer}")
    fun putManufacturer(
        @Path("Manufacturer") manufacturer: String
    ): Call<Product.ArProduct>

    @PUT("ArProduct/{Price}")
    fun putPrice(
        @Path("Price") price: Long
    ): Call<Product.ArProduct>

    @PUT("ArProduct/{Image}")
    fun putImage(
        @Path("Image") url: String
    ): Call<Product.ArProduct>
}