package com.arthe100.arshop.models

import com.google.gson.annotations.SerializedName

sealed class User{
    data class User(
        var username : String,
        var password : String,
        var email : String,
        var phone : String,
        val token : UserToken
    ) : com.arthe100.arshop.models.User()
    object GuestUser : com.arthe100.arshop.models.User()
}

data class UserToken(
    val username: String,
    val token: String
)

data class AuthUser(
    val password: String,
    val phone: String
)

data class UserProfile(
    val id: Long,
    @SerializedName("first_name") val fName : String,
    @SerializedName("last_name") val lName : String,
    @SerializedName("phone") val phone : String,
    val email : String,
    @SerializedName("social_security_number") val ssId : String
)

