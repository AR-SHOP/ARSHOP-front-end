package com.arthe100.arshop.models

data class User(
    val username : String
    , val password : String
    , val email : String
    , val phone : String
    , val token : UserToken
)



data class UserToken(
    val username: String,
    val token: String
)

data class AuthUser(
    val password: String,
    val phone: String
)
