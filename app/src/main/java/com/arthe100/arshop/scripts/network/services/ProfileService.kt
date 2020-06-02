package com.arthe100.arshop.scripts.network.services

import com.arthe100.arshop.models.UserProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ProfileService {

    @GET("profile/")
    suspend fun getInfo() : UserProfile

    @PATCH("profile/edit")
    suspend fun editInfo(@Body userProfile: UserProfile) : UserProfile
}