package com.arthe100.arshop.models

import com.google.gson.annotations.SerializedName

data class WishList (
    val id : Long,
    val user : Long,
    @SerializedName("wish_items") var wishListItems : List<Product>
)