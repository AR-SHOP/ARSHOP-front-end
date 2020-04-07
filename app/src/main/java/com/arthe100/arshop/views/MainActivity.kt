package com.arthe100.arshop.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.ar.ArManager
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val url = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arManager = ArManager(arFragment as ArFragment)
        arManager.setContext(this)
        arManager.setUri(url)
    }
}
