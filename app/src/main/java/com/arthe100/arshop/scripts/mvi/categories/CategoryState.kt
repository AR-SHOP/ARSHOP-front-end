package com.arthe100.arshop.scripts.mvi.categories

import com.arthe100.arshop.models.Category

sealed class CategoryState{
    object IdleState : CategoryState()
    object LoadingState : CategoryState()
    data class GetCategorySuccess(val categories: List<Category>) : CategoryState()
    data class Failure(val throwable: Throwable) : CategoryState()
}