package com.arthe100.arshop.scripts.mvi.Auth

import android.app.Application
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.google.gson.Gson

class UserSession{

    var user: User
    val application: Application

    constructor(application: Application ) : this(application , User.GuestUser)

    constructor(application: Application , user: User){
        this.user = user
        this.application = application
    }

    fun saveUser(user: User)
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        with(pref.edit()){
            putString("userData" , Gson().toJson(user))
            commit()
        }

        this.user =user
        this.user = user
    }

}
