package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.models.User

sealed class AuthState{
    object Idle : AuthState()
    object LoadingState : AuthState()
    data class Success(val user: User) : AuthState()
    data class Failure(val err : Throwable) : AuthState()
}