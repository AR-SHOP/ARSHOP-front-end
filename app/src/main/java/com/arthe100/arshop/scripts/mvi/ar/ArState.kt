package com.arthe100.arshop.scripts.mvi.ar

import com.google.ar.core.Anchor
import com.google.ar.sceneform.rendering.ModelRenderable

sealed class ArState {
    object IdleState : ArState()
    object LoadingState : ArState()
    data class ModelSuccess(val uri: String , val model : ModelRenderable,val anchor: Anchor) : ArState()
    data class ModelFailure(val err: Throwable): ArState()
}