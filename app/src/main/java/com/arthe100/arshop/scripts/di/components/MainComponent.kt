package com.arthe100.arshop.scripts.di.components

import android.content.Context
import com.arthe100.arshop.scripts.di.modules.MainModule
import com.arthe100.arshop.scripts.di.modules.SubComponentModules.MainSubComponentModule
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class , MainSubComponentModule::class])
interface MainComponent  {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : MainComponent
    }


    fun arComponent() : ArComponent.Factory

    fun inject(activity : MainActivity)
}