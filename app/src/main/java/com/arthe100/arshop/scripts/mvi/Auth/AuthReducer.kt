package com.arthe100.arshop.scripts.mvi.Auth

import com.arthe100.arshop.scripts.mvi.mviBase.Reducer
import com.arthe100.arshop.scripts.mvi.Auth.AuthInternalAction.*

class AuthReducer : Reducer<AuthInternalAction , AuthState>{

    override fun reduce(action: AuthInternalAction, state: AuthState): AuthState {
        return when(action){
            SignupInProgressAction -> AuthState.LoadingState
            is SignupSuccessAction -> AuthState.Success(action.user)
            is SignupErrorAction -> AuthState.Failure(action.err)
        }
    }
}