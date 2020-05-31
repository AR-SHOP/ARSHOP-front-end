package com.arthe100.arshop.models


data class Comment(
    val id: Long,
    val user: String,
    val title: String,
    val comment: String
)
