package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserSignUp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService  {
    @GET("users/")
    suspend fun getUsers() : List<User>


    @POST("signup/")
    suspend fun signup(@Body user: UserSignUp) : User
}