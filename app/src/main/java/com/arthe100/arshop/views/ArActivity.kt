package com.arthe100.arshop.views

import android.os.Bundle
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.ar.ArManager
import com.arthe100.arshop.scripts.messege.MessageManager
import com.google.ar.sceneform.ux.ArFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.ar_activity.*
import javax.inject.Inject

class ArActivity : DaggerAppCompatActivity() {

    val duckUrl = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
    val tableUrl = "https://poly.googleapis.com/downloads/fp/1586167353776716/8cnrwlAWqx7/cfVCFxWqtbc/Table_Large_Rectangular_01.gltf"
    val bedUrl = "https://poly.googleapis.com/downloads/fp/1586167422468753/8mkAgVYGbL4/5oNDqZI-I0J/Bed_01.gltf"

    @Inject lateinit var messageManager: MessageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_activity)
        messageManager.toast(this , "hey this works")
        val arManager = ArManager(this ,arFragment as ArFragment , R.layout.ar_model_detail , messageManager)
        arManager.setUri(duckUrl)

        btnBed.setOnClickListener{
            arManager.setUri(bedUrl)
        }
        btnTable.setOnClickListener{
            arManager.setUri(tableUrl)
        }
        btnDuck.setOnClickListener{
            arManager.setUri(duckUrl)
        }
    }
}
