package com.arthe100.arshop.models
import com.google.gson.annotations.SerializedName

data class Product(
    val id: Long,
    val name : String,
    val description : String,
    val manufacturer : String,
    val price : Long,
    @SerializedName("image") val thumbnail : String,
    @SerializedName("ar_model") val arModel: String,
    val comments: List<Comment>
)


