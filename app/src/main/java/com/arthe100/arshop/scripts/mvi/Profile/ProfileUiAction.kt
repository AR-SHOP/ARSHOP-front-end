package com.arthe100.arshop.scripts.mvi.Profile

import com.arthe100.arshop.scripts.mvi.mviBase.Action

sealed class ProfileUiAction : Action(){
    object GetHomePageProfileAction : ProfileUiAction()
}