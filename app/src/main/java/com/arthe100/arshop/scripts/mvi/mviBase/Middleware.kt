package com.arthe100.arshop.scripts.mvi.mviBase

import kotlinx.coroutines.flow.Flow

// gets an action and a state and creates a new action

interface Middleware <A , S>{
    fun bind(action: Flow<A>, state: Flow<S>) : Flow<A>
}