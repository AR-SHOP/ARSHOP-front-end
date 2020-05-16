package com.arthe100.arshop.scripts.mvi.Profile

import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserProfile

sealed class ProfileState {
    object Idle                                             : ProfileState()
    object LoadingState                                     : ProfileState()
    object LogoutState                                      : ProfileState()
    data class GetProfileSuccess(val userInfo: UserProfile) : ProfileState()
    data class GetProfileFailure(val throwable: Throwable)  : ProfileState()
}