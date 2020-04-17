package com.arthe100.arshop.scripts.mvi.Auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.scripts.mvi.mviBase.Action
import com.arthe100.arshop.scripts.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val userRepo : UserRepository) : ViewModel(){

    private val _currentViewState = MutableLiveData<AuthState>()
    val currentViewState : LiveData<AuthState>
        get() = _currentViewState

    init {
        _currentViewState.value = AuthState.Idle
    }

    fun onEvent(action: Action){

        when(action)
        {
            is AuthUiAction.SignupAction -> {
                handleSignUp(action)
            }
            is AuthUiAction.LoginAction -> {

            }
        }
    }

    private fun handleSignUp(action : AuthUiAction.SignupAction) {
        _currentViewState.value = AuthState.LoadingState

        viewModelScope.launch {
            val user = userRepo.Signup(
                email = action.email,
                username = action.username,
                password = action.password
            )
        }
    }

}