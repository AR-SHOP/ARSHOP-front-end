package com.arthe100.arshop.scripts.mvi.mviBase

import kotlinx.coroutines.flow.Flow

interface MviView<A , S> {
    val actions: Flow<A>
    fun render(state: S)
}