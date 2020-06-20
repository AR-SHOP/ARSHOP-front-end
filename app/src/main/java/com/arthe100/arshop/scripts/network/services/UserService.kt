package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.*
import retrofit2.http.*

interface UserService  {

    @POST("signup/")
    suspend fun signup(@Body user: SignupUser)

    @POST("login/")
    suspend fun login(@Body user: AuthUser) : UserToken

    @POST("signup/send-sms/")
    suspend fun getCode(@Body phone: PhoneNetwork) : List<CodeNetwork>


    //TODO()
    @POST("refresh-token/")
    fun refreshToken(@Body token: RefreshedTokenModel) : RefreshedTokenModel
}