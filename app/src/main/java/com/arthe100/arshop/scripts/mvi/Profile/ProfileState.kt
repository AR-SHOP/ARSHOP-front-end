package com.arthe100.arshop.scripts.mvi.Profile

import com.arthe100.arshop.models.User

sealed class ProfileState {
    object Idle : ProfileState()
    object LoadingState : ProfileState()
    data class GetProfileSuccess(val userInfo: User.User) : ProfileState()
    data class GetProfileFailure(val throwable: Throwable) : ProfileState()
}