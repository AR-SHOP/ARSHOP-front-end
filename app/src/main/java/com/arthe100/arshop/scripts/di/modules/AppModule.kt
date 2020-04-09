package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.scripts.messege.MessageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    companion object{
        @Provides
        fun messageManager() : MessageManager{
            return MessageManager()
        }
    }
}