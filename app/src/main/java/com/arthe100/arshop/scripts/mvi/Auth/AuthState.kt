package com.arthe100.arshop.scripts.mvi.Auth

class AuthState(
    val loading: Boolean,
    val err: Throwable? = null)