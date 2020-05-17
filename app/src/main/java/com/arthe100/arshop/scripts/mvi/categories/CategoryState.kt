package com.arthe100.arshop.scripts.mvi.categories

import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Product

sealed class CategoryState{
    object IdleState                                                : CategoryState()
    object LoadingState                                             : CategoryState()
    data class Failure(val throwable: Throwable)                    : CategoryState()
    data class GetProductSuccess(val products: List<Product>)       : CategoryState()
    data class GetCategorySuccess(val categories: List<Category>)   : CategoryState()
}