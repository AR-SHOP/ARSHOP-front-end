package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.network.services.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor (private val service : ProductService)
{

    suspend fun getProducts() : ProductState{
        return try {
            val product = service.getProducts()
            ProductState.GetProductsSuccess(product)
        }catch (throwable : Throwable)
        {
            ProductState.GetProductsFailure(throwable)
        }
    }
    suspend fun getProduct(id: Long) : ProductState{
        return try {
            val product = service.getProduct(id)
            ProductState.ProductDetailSuccess(product)
        }catch (throwable : Throwable)
        {
            ProductState.GetProductsFailure(throwable)
        }
    }

    suspend fun getHomeSales() : ProductState{
        return try {
            val homeSales = service.getHomeSales()
            ProductState.HomePageSalesSuccess(homeSales)
        }catch (throwable: Throwable){
            ProductState.HomePageSalesFailure(throwable)
        }
    }
}