package com.arthe100.arshop.scripts.mvi.base

interface IMviEvent {
    fun onEvent(action: UiAction)
}