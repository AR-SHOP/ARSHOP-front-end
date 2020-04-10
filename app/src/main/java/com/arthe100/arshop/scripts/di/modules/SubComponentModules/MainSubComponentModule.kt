package com.arthe100.arshop.scripts.di.modules.SubComponentModules

import com.arthe100.arshop.scripts.di.components.ArComponent
import dagger.Module

@Module(
        subcomponents = [ArComponent::class]
)
class MainSubComponentModule