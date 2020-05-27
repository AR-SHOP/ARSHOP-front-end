package com.arthe100.arshop.scripts.mvi.categories

import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.CurrentCategory

sealed class CategoryUiAction{
    object GetCategories                        : CategoryUiAction()
    data class GetCategoryProduct(val category: Category) : CategoryUiAction()
}