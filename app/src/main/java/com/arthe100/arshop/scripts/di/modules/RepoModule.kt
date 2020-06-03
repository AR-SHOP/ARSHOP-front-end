package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.network.services.*
import com.arthe100.arshop.scripts.repositories.*
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
    @JvmStatic
    @AppScope
    @Provides
    fun provideCategoryRepo(service: CategoryService) : CategoryRepository{
        return CategoryRepository(service)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideHomeRepo(service: HomeService) : HomeRepository{
        return HomeRepository(service)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideProfileRepo(service: ProfileService) : ProfileRepository{
        return ProfileRepository(service)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideWishListRepo(service: WishListService) : WishListRepository{
        return WishListRepository(service)
    }
}