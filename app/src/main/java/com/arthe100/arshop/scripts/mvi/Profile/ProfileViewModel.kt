package com.arthe100.arshop.scripts.mvi.Profile

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.ProfileRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModelBase(){

    var currentProfile: UserProfile? = null

    override fun onEvent(action: UiAction){
        when(action){
            is ProfileUiAction.GetHomePageProfileAction -> {
                if(currentProfile != null )
                    _currentViewState.value = ProfileState.GetProfileSuccess(currentProfile!!)
                else
                    _currentViewState.value = ViewState.LoadingState

                viewModelScope.launch {
                    _currentViewState.value = profileRepository.getInfo()
                }
            }
            is ProfileUiAction.LogoutAction -> {
                currentProfile = null
                _currentViewState.value = ProfileState.LogoutState
            }
            is ProfileUiAction.EditProfileInfoAction -> {
                if(currentProfile != null )
                    _currentViewState.value = ProfileState.EditProfileSuccess(currentProfile!!)
                else
                    _currentViewState.value = ViewState.LoadingState

                viewModelScope.launch {
                    _currentViewState.value = profileRepository.editInfo(currentProfile!!)
                }
            }
        }
    }
}