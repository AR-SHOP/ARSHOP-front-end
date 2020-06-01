package com.arthe100.arshop.scripts.mvi.Auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.scripts.mvi.base.AuthUiAction
import com.arthe100.arshop.scripts.mvi.base.UiAction
import com.arthe100.arshop.scripts.mvi.base.ViewModelBase
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.mvi.mviBase.Action
import com.arthe100.arshop.scripts.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val userRepo : UserRepository) : ViewModelBase(){


    private var _phone: String = ""
    var phone: String
        get() = _phone
        set(value){
            _phone = value
        }
    private var _password: String = ""
    var password: String
        get() = _password
        set(value){
            _password = value
        }

    fun logout(){
        _phone = ""
        _password = ""
    }

    override fun onEvent(action: UiAction){
        when(action)
        {
            is AuthUiAction.SignupAction -> {
                _phone = action.phone
                _password = action.password
                handleSignUp(action)
            }
            is AuthUiAction.LoginAction -> {
                handleLogin(action)
            }
            is AuthUiAction.CheckCodeAction -> {
                checkCode(action)
            }

            is AuthUiAction.LogoutAction -> {
                logout()
                _currentViewState.value = ViewState.IdleState
            }


        }
    }

    private fun checkCode(action: AuthUiAction.CheckCodeAction) {
        _currentViewState.value = ViewState.LoadingState

        viewModelScope.launch {
            _currentViewState.value = userRepo.checkCode(action.code)
        }
    }

    private fun handleSignUp(action : AuthUiAction.SignupAction) {
        _currentViewState.value = ViewState.LoadingState

        viewModelScope.launch {
            _currentViewState.value = userRepo.signup(
                password = action.password,
                phone = action.phone
            )
        }
    }

    private fun handleLogin(action: AuthUiAction.LoginAction){
        _currentViewState.value = ViewState.LoadingState
        viewModelScope.launch {
            _currentViewState.value = userRepo.login(action.password , action.phone)
        }
    }

}