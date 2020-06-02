package com.arthe100.arshop.models

import java.sql.Timestamp
import java.time.LocalDateTime


data class Comment(
    val id: Long,
    val user: String,
    val content: String,
    val rating: Float,
    val dateTime: LocalDateTime
)
