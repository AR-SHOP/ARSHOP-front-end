package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.network.services.UserService
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RetrofitModule {

    private val baseUrl = "http://babk.pythonanywhere.com/shop_core/api/"

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
    fun provideOkHttpClient(interceptor : Interceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    @JvmStatic
    @AppScope
    @Provides
    fun provideInterceptor() : Interceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @JvmStatic
    @AppScope
    @Provides
    fun provideUserService(retrofit: Retrofit) : UserService{
        return retrofit.create(UserService::class.java)
    }
}