package com.arthe100.arshop.scripts.mvi.Auth

import android.app.Application
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.google.gson.Gson
import javax.inject.Inject

class UserSession{

    @Inject constructor(application: Application ){
        this.application = application
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        val userStr = pref.getString("userData" , null)
        if(userStr == null)
            this.user = User.GuestUser
        else
            this.user = Gson().fromJson(userStr , User.User::class.java)
    }

    var user: User
    val application: Application

    constructor(application: Application , user: User){
        this.user = user
        this.application = application
    }

    fun saveUser(user: User)
    {
        this.user =user
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        with(pref.edit()){
            putString("userData" , Gson().toJson(user))
            commit()
        }
    }

    fun logout(){
        this.user = User.GuestUser
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        with(pref.edit()){
            clear()
            commit()
        }
    }

}
