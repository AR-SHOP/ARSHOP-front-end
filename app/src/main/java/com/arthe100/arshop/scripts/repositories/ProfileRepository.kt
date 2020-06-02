package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val service: ProfileService){

    private val TAG = ProfileRepository::class.simpleName

    suspend fun getInfo(): ViewState {
        return try {
            ProfileState.GetProfileSuccess(service.getInfo())

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }

    suspend fun editInfo(userProfile: UserProfile): ViewState {
        return try {
            ProfileState.EditProfileSuccess(service.editInfo(userProfile))

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }
}