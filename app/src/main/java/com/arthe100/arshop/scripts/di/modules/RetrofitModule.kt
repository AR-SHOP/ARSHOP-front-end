package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.network.interceptors.TokenAuthenticator
import com.arthe100.arshop.scripts.network.interceptors.TokenInterceptor
import com.arthe100.arshop.scripts.network.services.*
import com.arthe100.arshop.scripts.repositories.UserRepository
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RetrofitModule {

    private val baseUrl = "http://babk.pythonanywhere.com/core/api/"

    @JvmStatic
    @AppScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideLoggingInterceptor() : Interceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideOkHttpClient(interceptor : Interceptor, tokenInterceptor: TokenInterceptor) : OkHttpClient{

        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideTokenInterceptor(session: UserSession) : TokenInterceptor{
        return TokenInterceptor(session)
    }


    @JvmStatic
    @AppScope
    @Provides
    fun provideTokenAuthenticator(session: UserSession , userService: Lazy<UserService>) : TokenAuthenticator{
        return TokenAuthenticator(session , userService)
    }



    @JvmStatic
    @AppScope
    @Provides
    fun provideUserService(retrofit: Retrofit) : UserService{
        return retrofit.create(UserService::class.java)
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideProductService(retrofit: Retrofit) : ProductService{
        return retrofit.create(ProductService::class.java)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideCartService(retrofit: Retrofit) : CartService{
        return retrofit.create(CartService::class.java)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideCategoryService(retrofit: Retrofit) : CategoryService{
        return retrofit.create(CategoryService::class.java)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideHomeService(retrofit: Retrofit) : HomeService{
        return retrofit.create(HomeService::class.java)
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideAddressService(retrofit: Retrofit) : AddressService{
        return retrofit.create(AddressService::class.java)
    }
}