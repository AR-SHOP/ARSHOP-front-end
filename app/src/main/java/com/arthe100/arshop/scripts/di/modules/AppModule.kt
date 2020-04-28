package com.arthe100.arshop.scripts.di.modules

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.ar.InfoManager.BasicArInfoCardManager
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.google.ar.sceneform.Scene
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @AppScope
    @Provides
    fun messageManager() : MessageManager{
        return MessageManager()
    }


    @JvmStatic
    @AppScope
    @Provides
    fun provideUser(application: Application) : UserSession {
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        val userStr = pref.getString("userData" , null) ?: return UserSession(application)
        return UserSession(application, Gson().fromJson(userStr , User.User::class.java))
    }
}