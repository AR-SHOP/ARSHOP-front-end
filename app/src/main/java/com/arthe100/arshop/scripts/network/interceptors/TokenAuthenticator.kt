package com.arthe100.arshop.scripts.network.interceptors

import com.arthe100.arshop.models.RefreshedTokenModel
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.base.AuthState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.UserService
import com.arthe100.arshop.scripts.repositories.UserRepository
import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val session: UserSession,
    private val service: Lazy<UserService>
) : Authenticator {

    private var user: User.User? = null

    init {
        user = when(val temp = session.user) {
            is User.User -> temp
            else -> null
        }
    }


    override fun authenticate(route: Route?, response: Response): Request? {
        if(user == null) return null
        if(isRequestWithAccessToken(response)) return null
        val token = user?.token!!
        return try {
            val res = service.get().refreshToken(RefreshedTokenModel(token.token))
            newRequestWithAccessToken(response.request() , res.newToken)
        }catch (t: Throwable){
            null
        }
    }

    private fun isRequestWithAccessToken(response: Response) : Boolean {
        val header = response.request().header("Authorization");
        return header != null && header.startsWith("Bearer");
    }

    private fun newRequestWithAccessToken(request: Request , token: String) : Request{
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build();
    }

}