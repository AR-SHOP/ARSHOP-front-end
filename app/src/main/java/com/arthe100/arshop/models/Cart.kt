package com.arthe100.arshop.models

import com.google.gson.annotations.SerializedName


data class Cart(
    val id: Long,
    val user: Long,
    val details: CartDetails,
    @SerializedName("cart_items") val cartItems: List<CartItem>
)


data class CartDetails(
    @SerializedName("total_quantity") val totalQuantity : Int,
    @SerializedName("total_price") val totalPrice: Long
)



data class CartItem(
    val product: Product,
    var quantity: Int,
    val subtotal: Long
)



data class AddCart(
    @SerializedName("product_id") val id : Long,
    @SerializedName("quantity") val quantity : Int
)

data class RemoveCart(
    @SerializedName("product_id") val id : Long
)