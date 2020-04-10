package com.arthe100.arshop.scripts.di.components

import com.arthe100.arshop.scripts.di.modules.AuthModule
import com.arthe100.arshop.scripts.di.scopes.ActivityScope
import dagger.Subcomponent

@Subcomponent(
        modules = [AuthModule::class]
)
interface AuthComponent{

    @Subcomponent.Factory
    interface Factory{
        fun create() : AuthComponent
    }

}