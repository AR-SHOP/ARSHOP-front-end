package com.arthe100.arshop.scripts.mvi.Products

import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product

sealed class ProductState {
    object Idle : ProductState()
    object LoadingState : ProductState()
    data class GetProductsSuccess(val products: List<Product>) : ProductState()
    data class ProductDetailSuccess(val product: Product) : ProductState()
    data class GetProductsFailure(val throwable: Throwable) : ProductState()
    data class HomePageSalesSuccess(val sales: List<HomeSales>) : ProductState()
    data class HomePageSalesFailure(val throwable: Throwable) : ProductState()

}