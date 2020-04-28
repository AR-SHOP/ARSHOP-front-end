package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.network.services.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor (private val service : ProductService)
{

    suspend fun getProducts() : ProductState{
        return try {
            val product = service.getProduct()
            ProductState.GetProductsSuccess(product)
        }catch (throwable : Throwable)
        {
            ProductState.GetProductsFaliure(throwable)
        }
    }
}