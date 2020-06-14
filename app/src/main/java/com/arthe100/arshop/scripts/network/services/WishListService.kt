package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.WishList
import com.arthe100.arshop.models.WishListProductID
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface WishListService {

    @GET("wish-list/")
    suspend fun getWishList() : WishList

    @POST("wish-list/add/")
    suspend fun addWishList(@Body wishListProductID: WishListProductID) : WishList

    @PUT("wish-list/remove/")
    suspend fun deleteWishList(@Body wishListProductID: WishListProductID) : WishList
}