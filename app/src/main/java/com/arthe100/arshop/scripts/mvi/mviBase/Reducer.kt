package com.arthe100.arshop.scripts.mvi.mviBase

// gets and action and an state and creates a new state

interface Reducer <A , S>{
    fun reduce(action: A , state: S) : S
}