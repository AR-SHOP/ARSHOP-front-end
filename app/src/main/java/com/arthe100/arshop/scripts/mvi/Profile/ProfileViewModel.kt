package com.arthe100.arshop.scripts.mvi.Profile

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.Address
import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.ProfileRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.arthe100.arshop.scripts.repositories.AddressRepository
import com.arthe100.arshop.scripts.repositories.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val profileRepository: ProfileRepository
) : ViewModelBase(){
    var currentProfile: UserProfile? = null
    var currentAddress: Address? =null
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
            is ProfileUiAction.GetAddressListAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = addressRepository.getAll()
                }
            }
            is ProfileUiAction.CreateAddressAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = addressRepository.create(action.address)
                }
            }
            is ProfileUiAction.GetAddressDetail -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = addressRepository.get(action.id)
                }
            }
            is ProfileUiAction.UpdateAddressAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = addressRepository.update(action.address)
                }
            }
            is ProfileUiAction.DeleteAddressAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = addressRepository.delete(action.id)
                }
            }
        }
    }
}