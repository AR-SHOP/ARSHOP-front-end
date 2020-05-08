package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.network.services.CartService
import com.arthe100.arshop.scripts.network.services.ProductService
import com.arthe100.arshop.scripts.network.services.UserService
import com.arthe100.arshop.scripts.repositories.CartRepository
import com.arthe100.arshop.scripts.repositories.ProductRepository
import com.arthe100.arshop.scripts.repositories.UserRepository
import dagger.Module
import dagger.Provides


@Module
object RepoModule {

    @JvmStatic
    @AppScope
    @Provides
    fun provideUserRepo(service: UserService) : UserRepository{
        return UserRepository(service)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideProductRepo(service: ProductService) : ProductRepository{
        return ProductRepository(service)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideCartRepo(service: CartService) : CartRepository{
        return CartRepository(service)
    }

}