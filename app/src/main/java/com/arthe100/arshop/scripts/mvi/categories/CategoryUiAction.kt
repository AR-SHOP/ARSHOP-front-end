package com.arthe100.arshop.scripts.mvi.categories

import com.arthe100.arshop.models.Category

sealed class CategoryUiAction{
    object GetCategories                        : CategoryUiAction()
    data class GetCategoryProduct(val category: Category) : CategoryUiAction()
}