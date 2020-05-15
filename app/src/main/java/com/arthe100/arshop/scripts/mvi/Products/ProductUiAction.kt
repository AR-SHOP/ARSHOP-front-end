package com.arthe100.arshop.scripts.mvi.Products

import com.arthe100.arshop.models.Product

sealed class ProductUiAction {

    object GetHomePageProducts : ProductUiAction()
    object GetHomePageSales : ProductUiAction()
    data class GetProductDetails(val product: Product) : ProductUiAction()
}