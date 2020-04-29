package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.*
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Profile.ProfileState
import com.arthe100.arshop.scripts.network.services.UserService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService) {

    private val TAG = UserRepository::class.simpleName

    suspend fun getInfo(): ProfileState {
        return try {
            val userInfo = service.getInfo()
            ProfileState.GetProfileSuccess()

        }catch (t: Throwable) {
            ProfileState.GetProfileFailure(t)
        }
    }

    suspend fun signup(password: String, phone: String): AuthState {
        return try {
            val user = AuthUser(password , phone)
            service.signup(user)
            AuthState.SingupSuccess
        }catch (t: Throwable){
            AuthState.Failure(t)
        }
    }

    suspend fun login(password: String , phone: String) : AuthState{
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
            AuthState.Failure(t)
        }
    }

    suspend fun checkCode(code: String) : AuthState{
        delay(500)
        return if(code == "12345")
            AuthState.CodeSuccess
        else
            AuthState.Failure(Throwable("wrongCode"))
    }
}