package com.arthe100.arshop.scripts.mvi.mviBase

abstract class Store<A , S> (
    private val reducer: Reducer<A, S>,
    private val middleware: List<Middleware<A, S>>,
    private val initialState: S){

    abstract fun wire()
    abstract fun bind(view : MviView<A, S>)
}