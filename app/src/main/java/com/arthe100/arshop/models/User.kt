package com.arthe100.arshop.models

import com.arthe100.arshop.models.User

sealed class User{
    data class User(
        val username : String,
        val password : String,
        val email : String,
        val phone : String,
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
