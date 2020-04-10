package com.arthe100.arshop.scripts.di.modules

import android.content.Context
import com.arthe100.arshop.scripts.ar.InfoManager.BasicArInfoCardManager
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.scopes.FragmentScope
import com.google.ar.sceneform.Scene
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ArModule{

    @JvmStatic
    @Provides
    @FragmentScope
    fun createInfoCard(context: Context , scene: Scene) : IInfoManager {
        return BasicArInfoCardManager(context , scene)
    }
}