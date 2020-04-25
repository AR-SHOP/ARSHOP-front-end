package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.network.services.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor (private val service : ProductService)
{

    suspend fun getUsers() : List<Product> {
        return service.getProduct()
    }
}