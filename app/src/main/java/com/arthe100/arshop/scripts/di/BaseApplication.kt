package com.arthe100.arshop.scripts.di

import android.app.Application
import android.content.Context
import com.arthe100.arshop.scripts.di.components.AppComponent
import com.arthe100.arshop.scripts.di.components.ArComponent
import com.arthe100.arshop.scripts.di.components.DaggerAppComponent
import com.arthe100.arshop.scripts.di.components.MainComponent

class BaseApplication : Application(){

    lateinit var appComponent : AppComponent
    private var mainComponent : MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent(){
        appComponent = DaggerAppComponent
                .factory()
                .create(this)
    }

    fun mainComponent(context: Context) : MainComponent{

        if(mainComponent == null)
            mainComponent = appComponent
                    .mainComponent()
                    .create(context)

        return mainComponent!!
    }


    fun releaseArComponent(){
        mainComponent = null
    }


}