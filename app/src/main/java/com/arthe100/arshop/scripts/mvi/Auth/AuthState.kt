package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserToken

sealed class AuthState{
    object Idle : AuthState()
    object LoadingState : AuthState()
    object CodeSuccess : AuthState()
    object SingupSuccess : AuthState()
    data class LoginSuccess(val user: User): AuthState()
    data class Failure(val err : Throwable) : AuthState()
}