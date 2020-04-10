package com.arthe100.arshop.scripts.di.modules.SubComponentModules

import com.arthe100.arshop.scripts.di.components.AuthComponent
import com.arthe100.arshop.scripts.di.components.MainComponent
import dagger.Module


@Module(
        subcomponents = [MainComponent::class, AuthComponent::class]
)
class AppSubComponentModule