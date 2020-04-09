package com.arthe100.arshop.scripts.di.components

import android.app.Application
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.di.modules.ActivityBuildersModule
import com.arthe100.arshop.scripts.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AndroidSupportInjectionModule::class
            , AppModule::class
            , ActivityBuildersModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication>{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}