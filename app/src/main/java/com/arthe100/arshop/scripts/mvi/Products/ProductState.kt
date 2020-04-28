package com.arthe100.arshop.scripts.mvi.Products

import com.arthe100.arshop.models.Product

sealed class ProductState {
    object Idle : ProductState()
    object LoadingState : ProductState()
    data class GetProductsSuccess(val products: List<Product>) : ProductState()
    data class GetProductsFaliure(val throwable: Throwable) : ProductState()
}