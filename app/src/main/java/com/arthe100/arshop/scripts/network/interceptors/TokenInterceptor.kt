package com.arthe100.arshop.scripts.network.interceptors

import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val session: UserSession) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        return when(val currentUser = session.user)
        {
            is User.User-> {
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization" , "Bearer " + currentUser.token.token)
                    .build()
                chain.proceed(newRequest )
            }
            else -> {
                chain.proceed(originalRequest)
            }
        }
    }

}