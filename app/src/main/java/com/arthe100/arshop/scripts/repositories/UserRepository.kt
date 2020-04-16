package com.arthe100.arshop.scripts.repositories

import android.util.Log
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.network.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService) {

    private val TAG = UserRepository::class.simpleName
    suspend fun getUsers() : List<User> {
        return service.getUsers()
    }

}