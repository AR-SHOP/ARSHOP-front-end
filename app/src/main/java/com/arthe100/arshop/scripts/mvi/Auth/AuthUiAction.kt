package com.arthe100.arshop.scripts.mvi.Auth

sealed class AuthUiAction {
    class SignupAction(val username: String , val password: String , val email: String) : AuthUiAction()
    class LoginAction(val username: String , val password: String) : AuthUiAction()
}