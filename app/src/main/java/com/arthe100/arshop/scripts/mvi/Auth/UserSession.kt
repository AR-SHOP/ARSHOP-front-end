package com.arthe100.arshop.scripts.mvi.Auth

import android.app.Application
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.arthe100.arshop.models.UserProfile
import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.google.gson.Gson
import javax.inject.Inject
@AppScope
class UserSession @Inject constructor(val application: Application) {

    var user: User
    var profile: UserProfile? = null

    init {
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        val userStr = pref.getString("userData" , null)
        val profileStr = pref.getString("profile" , null)
        if(userStr == null)
            this.user = User.GuestUser
        else
            this.user = Gson().fromJson(userStr , User.User::class.java)

        if(profileStr != null)
            this.profile = Gson().fromJson(profileStr , UserProfile::class.java)
    }

    fun saveProfile(profile: UserProfile){
        this.profile = profile
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        with(pref.edit()){
            putString("profile" , Gson().toJson(profile))
            commit()
        }
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
