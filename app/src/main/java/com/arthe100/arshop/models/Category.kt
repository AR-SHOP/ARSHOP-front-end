package com.arthe100.arshop.models

data class Category(
    val id: Long,
    val title: String,
    val description: String,
    val image: String
)


data class CurrentCategory(
    val products: List<Product>,
    val info: Category
)
