package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.User
import retrofit2.http.GET

interface UserService  {
    @GET("users/")
    suspend fun getUsers() : List<User>
}