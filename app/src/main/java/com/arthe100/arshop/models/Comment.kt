package com.arthe100.arshop.models

import java.sql.Timestamp


data class Comment(
    val id: Long,
    val user: String,
    val content: String,
    val rating: Float,
    val timestamp: Timestamp
)
