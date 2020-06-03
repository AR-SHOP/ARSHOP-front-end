package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.Address
import retrofit2.http.*

interface AddressService {
    @GET("address/")
    suspend fun getAll() : List<Address>

    @GET("address/{id}")
    suspend fun get(@Path("id") id: Long) : Address

    @POST("create-address/")
    suspend fun create(@Body address:Address) : Address

    @PUT("address/{id}")
    suspend fun update(@Path("id") id: Long) : Address

    @DELETE("address/{id}")
    suspend fun delete(@Path("id") id: Long)

}