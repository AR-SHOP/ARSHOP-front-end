package com.arthe100.arshop.scripts.mvi.Auth


sealed class AuthInternalAction{
    object SignupInprogressAction : AuthInternalAction()
    object SignupSuccessAction : AuthInternalAction()
    class SignupErrorAction(val err: Throwable) : AuthInternalAction()
}
