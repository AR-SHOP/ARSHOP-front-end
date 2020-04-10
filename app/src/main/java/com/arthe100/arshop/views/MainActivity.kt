package com.arthe100.arshop.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.ar.CustomArFragment
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.di.components.MainComponent
import com.arthe100.arshop.scripts.messege.MessageManager
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val TAG : String? = MainActivity::class.simpleName
    val duckUrl = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
    val bedUrl = "https://poly.googleapis.com/downloads/fp/1586167422468753/8mkAgVYGbL4/5oNDqZI-I0J/Bed_01.gltf"

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

    override fun inject() {
        Log.d(TAG , "injecting MainActivity")
        (application as BaseApplication).appComponent.mainComponent().create(this).inject(this)
        Log.d(TAG , "injected MainActivity")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_activity)
        messageManager.toast(this , "hey this works")

        loadFragment(customArFragment)
    }


    private fun loadFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
    }

}
