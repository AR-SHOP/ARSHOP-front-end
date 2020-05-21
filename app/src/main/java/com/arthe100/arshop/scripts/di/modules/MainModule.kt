package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.scripts.ar.InfoManager.BasicArInfoCardManager
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.scopes.MainScope
import dagger.Module
import dagger.Provides

@Module
object MainModule{

    @JvmStatic
    @Provides
    @MainScope
    fun createInfoCard(basicArInfoCardManager: BasicArInfoCardManager) : IInfoManager {
        return basicArInfoCardManager
    }

//    @JvmStatic
//    @MainScope
//    @Provides
//    fun provideDialogBox() : DialogBoxManager {
//        return DialogBoxManager()
//    }
}
