package com.arthe100.arshop.scripts.di.modules

import android.content.Context
import com.arthe100.arshop.scripts.ar.InfoManager.BasicArInfoCardManager
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.messege.MessageManager
import com.google.ar.sceneform.Scene
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
}