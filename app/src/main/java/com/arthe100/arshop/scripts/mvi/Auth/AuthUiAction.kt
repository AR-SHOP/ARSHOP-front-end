package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.scripts.mvi.mviBase.Action

sealed class AuthUiAction : Action(){
    data class SignupAction(val username: String , val password: String , val email: String) : AuthUiAction()
    data class LoginAction(val username: String , val password: String) : AuthUiAction()
}