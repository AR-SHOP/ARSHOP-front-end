package com.arthe100.arshop.scripts.di.components

import android.content.Context
import com.arthe100.arshop.scripts.di.modules.MainModule
import com.arthe100.arshop.scripts.di.modules.SubComponentModules.MainSubComponentModule
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.Adapters.ViewPagerAdapter
import com.arthe100.arshop.views.MainActivity
import com.arthe100.arshop.views.fragments.*
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@MainScope
@Subcomponent(modules = [MainModule::class , MainSubComponentModule::class])
interface MainComponent  {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : MainComponent
    }


    fun arComponent() : ArComponent.Factory

    fun inject(activity : MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: CategoriesFragment)
    fun inject(fragment: CartFragment)
    fun inject(fragment: LoginFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(fragment: VerifyFragment)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: OrdersFragment)
    fun inject(fragment: CustomerCartFragment)
    fun inject(viewPagerAdapter: ViewPagerAdapter)
}