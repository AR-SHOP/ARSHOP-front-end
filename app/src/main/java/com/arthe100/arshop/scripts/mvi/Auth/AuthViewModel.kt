package com.arthe100.arshop.scripts.mvi.Auth

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.scripts.mvi.base.*
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

    var code: String? = null

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
                _currentViewState.value = checkCode(action)
            }

            is AuthUiAction.LogoutAction -> {
                logout()
                _currentViewState.value = ViewState.IdleState
            }

            is AuthUiAction.GetCodeAction -> {
                _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = userRepo.getCode(action.phone)
                }
            }


        }
    }


    private fun checkCode(action: AuthUiAction.CheckCodeAction) : ViewState{
        return when (code) {
            null -> ViewState.Failure(Throwable("No Code"))
            action.code -> AuthState.CodeSuccess
            else -> ViewState.Failure(Throwable("Wrong Code"))
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