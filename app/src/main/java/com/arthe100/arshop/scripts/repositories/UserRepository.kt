package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.*
import com.arthe100.arshop.scripts.mvi.base.AuthState
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.UserService
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.Exception

class UserRepository @Inject constructor(private val service: UserService) {

    private val TAG = UserRepository::class.simpleName

    suspend fun getInfo(): ViewState {
        return try {
            ProfileState.GetProfileSuccess(service.getInfo())

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }

    suspend fun signup(password: String, phone: String): ViewState {
        return try {
            val user = AuthUser(password , phone)
            service.signup(user)
            AuthState.SingupSuccess
        }catch (t: Throwable){
            ViewState.Failure(t)
        }
    }

    suspend fun login(password: String , phone: String) : ViewState {
        return try {

            val user = AuthUser(password , phone)
            val token = service.login(user)
            val currentUser = User.User(
                username = token.username,
                password = password,
                email = "",
                phone = phone,
                token = token
            )
            return AuthState.LoginSuccess(currentUser)

        }catch (t: Throwable){
            ViewState.Failure(t)
        }
    }

    suspend fun checkCode(code: String) : ViewState {
        delay(500)
        return if(code == "12345")
            AuthState.CodeSuccess
        else
            ViewState.Failure(Throwable("wrongCode"))
    }
}