package com.arthe100.arshop.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*


data class Comment(
    val id: Long,
    val user: String,
    val content: String,
    val rating: Float,
    val timestamp: Timestamp
)

data class CommentNetwork(
    @SerializedName("product_id") val productId: Long,
    val content: String,
    val rating: Float,
    val anonymous: Int
)
