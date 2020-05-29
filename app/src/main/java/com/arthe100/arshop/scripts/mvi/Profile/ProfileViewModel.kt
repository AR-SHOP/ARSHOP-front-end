package com.arthe100.arshop.scripts.mvi.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel(){

    private val _currentViewState = MutableLiveData<ProfileState>()
    val currentViewState : LiveData<ProfileState>
        get() = _currentViewState

    var currentProfile: UserProfile? = null

    init {
        _currentViewState.value = ProfileState.Idle
    }




    fun onEvent(action: ProfileUiAction){
        when(action){
            is ProfileUiAction.GetHomePageProfileAction -> {
                if(currentProfile != null )
                    _currentViewState.value = ProfileState.GetProfileSuccess(currentProfile!!)
                else
                    _currentViewState.value = ProfileState.LoadingState

                viewModelScope.launch {
                    _currentViewState.value = userRepository.getInfo()
                }
            }
            is ProfileUiAction.LogoutAction -> {
                currentProfile = null
                _currentViewState.value = ProfileState.LogoutState
            }
        }
    }
}