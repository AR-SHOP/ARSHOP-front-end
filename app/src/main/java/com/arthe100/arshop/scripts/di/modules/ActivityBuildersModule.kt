package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.views.ArActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeAcitivy() : ArActivity
}