package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.scripts.di.scopes.MainScope
import dagger.Module
import dagger.Provides

@Module
object MainModule{

    @JvmStatic
    @MainScope
    @Provides
    fun createArFragment() : CustomArFragment {
        return CustomArFragment()
    }


    @JvmStatic
    @MainScope
    @Provides
    fun provideFakeClass() : FakeClass{
        return FakeClass()
    }

}
class FakeClass


