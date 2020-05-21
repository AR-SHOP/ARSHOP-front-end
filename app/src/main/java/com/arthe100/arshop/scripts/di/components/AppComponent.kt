package com.arthe100.arshop.scripts.di.components

import android.app.Application
import com.arthe100.arshop.scripts.di.FragmentModule
import com.arthe100.arshop.scripts.di.ViewModelModule
import com.arthe100.arshop.scripts.di.modules.AppModule
import com.arthe100.arshop.scripts.di.modules.RepoModule
import com.arthe100.arshop.scripts.di.modules.RetrofitModule
import com.arthe100.arshop.scripts.di.modules.SubComponentModules.AppSubComponentModule
import com.arthe100.arshop.scripts.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
        modules = [AppModule::class
            , AppSubComponentModule::class
            , RetrofitModule::class
            , RepoModule::class
            , ViewModelModule::class
            , FragmentModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application) : AppComponent
    }

    fun mainComponent() : MainComponent.Factory
}


//@Component.Builder
//interface Builder{
//
//    @BindsInstance
//    fun application(application: Application) : Builder
//
//    @BindsInstance
//    fun context
//
//    fun build() : AppComponent
//}