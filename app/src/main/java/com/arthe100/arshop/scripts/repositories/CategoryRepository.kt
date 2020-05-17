package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.network.services.CategoryService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val service: CategoryService){

    suspend fun getCategories() : CategoryState {
        return try {
            CategoryState.GetCategorySuccess(service.getCategories())
        }catch (throwable: Throwable)
        {
            CategoryState.Failure(throwable)
        }

    }

    suspend fun getProducts(id: Long): CategoryState {
        return try {
            CategoryState.GetProductSuccess(service.getProducts(id))
        }catch (throwable: Throwable){
            CategoryState.Failure(throwable)
        }
    }

}