package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserSignUp
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.network.services.UserService
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService) {

    private val TAG = UserRepository::class.simpleName
    suspend fun getUsers() : List<User> {
        return service.getUsers()
    }

    suspend fun Signup(email: String, password: String, username: String): AuthState {
        try {
            val user = service.signup(
                UserSignUp(
                username = username,
                password = password,
                email = email)
            )
            return AuthState.Success(user)
        }catch (t: Throwable){
            return AuthState.Failure(t)
        }
    }

}