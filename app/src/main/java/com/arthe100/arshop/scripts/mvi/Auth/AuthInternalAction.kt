package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.mvi.mviBase.Action


sealed class AuthInternalAction : Action(){
    object SignupInProgressAction : AuthInternalAction()
    class SignupSuccessAction(val user: User) : AuthInternalAction()
    class SignupErrorAction(val err: Throwable) : AuthInternalAction()
}
