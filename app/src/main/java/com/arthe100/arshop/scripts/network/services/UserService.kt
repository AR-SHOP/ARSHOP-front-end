package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService  {

    @POST("signup/")
    suspend fun signup(@Body user: AuthUser)

    @POST("login/")
    suspend fun login(@Body user: AuthUser) : UserToken

}