package com.arthe100.arshop.scripts.di.components

import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.scripts.di.modules.ArModule
import com.arthe100.arshop.scripts.di.scopes.FragmentScope
import com.google.ar.sceneform.Scene
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
        modules = [ArModule::class]
)
interface ArComponent{

    @Subcomponent.Factory
    interface Factory{
        fun create() : ArComponent
    }


}