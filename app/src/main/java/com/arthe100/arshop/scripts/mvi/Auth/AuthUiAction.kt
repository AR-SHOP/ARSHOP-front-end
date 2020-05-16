package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.scripts.mvi.mviBase.Action

sealed class AuthUiAction : Action(){
    object LogoutAction                                                 : AuthUiAction()
    data class CheckCodeAction(val code: String)                        : AuthUiAction()
    data class SignupAction(val password: String , val phone: String)   : AuthUiAction()
    data class LoginAction(val password: String , val phone: String)    : AuthUiAction()
}