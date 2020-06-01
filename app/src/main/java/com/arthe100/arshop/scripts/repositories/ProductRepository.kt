package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.base.ProductState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor (private val service : ProductService)
{

    suspend fun getProducts() : ViewState{
        return try {
            val product = service.getProducts()
            ProductState.ProductsSuccess(product)
        }catch (throwable : Throwable)
        {
            ViewState.Failure(throwable)
        }
    }
    suspend fun getProduct(id: Long) : ViewState{
        return try {
            val product = service.getProduct(id)
            ProductState.ProductDetailSuccess(product)
        }catch (throwable : Throwable)
        {
            ViewState.Failure(throwable)
        }
    }

}