package com.arthe100.arshop.scripts.network.api

import com.arthe100.arshop.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi
{
    @GET("User")
    fun getUser(
        @Query("ID") id: Long,
        @Query("Username") username: String,
        @Query("Password") password: String,
        @Query("E-Mail") email: String,
        @Query("Phone") phone: String
    ): Call<List<User>>

    @POST("User")
    fun createUser(
        @Body user: User
    ): Call<User>

    @PUT("User/{ID}")
    fun putID(
        @Path("ID") id: Long
    ): Call<User>

    @PUT("User/{Username}")
    fun putUser(
        @Path("Username") username: String
    ): Call<User>

    @PUT("User/{Password}")
    fun putPassword(
        @Path("Password") password: String
    ): Call<User>

    @PUT("User/{E-Mail}")
    fun putEmail(
        @Path("E-mail") email: String
    ): Call<User>

    @PUT("User/{Phone}")
    fun putPhone(
        @Path("Phone") phone: String
    ): Call<User>
}