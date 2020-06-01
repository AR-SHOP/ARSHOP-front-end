package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.base.CategoryState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.CategoryService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val service: CategoryService){

    suspend fun getCategories() : ViewState {
        return try {
            CategoryState.GetCategorySuccess(service.getCategories())
        }catch (throwable: Throwable)
        {
            ViewState.Failure(throwable)
        }

    }

    suspend fun getProducts(id: Long): ViewState {
        return try {
            CategoryState.GetProductSuccess(service.getProducts(id))
        }catch (throwable: Throwable){
            ViewState.Failure(throwable)
        }
    }

}