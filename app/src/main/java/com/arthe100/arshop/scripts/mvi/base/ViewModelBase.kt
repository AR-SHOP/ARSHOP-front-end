package com.arthe100.arshop.scripts.mvi.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelBase : ViewModel() , IMviEvent{
    protected val _currentViewState = MutableLiveData<ViewState>(ViewState.IdleState)
    val currentViewState : LiveData<ViewState>
        get() = _currentViewState
}