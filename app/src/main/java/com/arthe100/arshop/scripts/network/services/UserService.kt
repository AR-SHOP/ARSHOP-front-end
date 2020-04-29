package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService  {

    @GET("profile/")
    suspend fun getInfo(
        @Query("ID") id: Long,
        @Query("Username") username: String,
        @Query("Password") password: String,
        @Query("E-Mail") email: String,
        @Query("Phone") phone: String
    )

    @POST("signup/")
    suspend fun signup(@Body user: AuthUser)

    @POST("login/")
    suspend fun login(@Body user: AuthUser) : UserToken
}