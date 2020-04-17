package com.arthe100.arshop.models

import com.squareup.moshi.JsonClass

data class User(
    val id : Long
    , val username : String
    , val password: String
    , val email: String
    , val phone: String
)

data class UserSignUp(
    val username : String,
    val password: String,
    val email: String
)
