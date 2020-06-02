package com.arthe100.arshop.models

data class Address(
    val firstName: String,
    val lastName: String,
    val nationalId: String,
    val phone: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val addressLine: String
)