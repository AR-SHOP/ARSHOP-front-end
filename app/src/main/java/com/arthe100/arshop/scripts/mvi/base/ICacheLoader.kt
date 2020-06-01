package com.arthe100.arshop.scripts.mvi.base

interface ICacheLoader {
    fun load(func: (ViewState) -> Unit)
}