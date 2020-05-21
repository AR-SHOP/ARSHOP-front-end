package com.arthe100.arshop.scripts.ar.InfoManager

import android.content.Context
import android.widget.Button
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.scopes.FragmentScope
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import javax.inject.Inject

@MainScope
class BasicArInfoCardManager @Inject constructor(private val context: Context) : IInfoManager {

    private val TAG = BasicArInfoCardManager::class.simpleName
    
    private lateinit var infoCard : ViewRenderable
    private lateinit var scene: Scene
    private var parent : Node? = null
    private var current : Node? = null
    private var onDelete : (() -> Unit)? = null


    override fun init(scene: Scene){

        this.scene = scene

        ViewRenderable.builder()
                .setView(context, R.layout.ar_model_detail)
                .build()
                .thenAccept{
                    infoCard = it
                    it.isShadowCaster = false
                    it.isShadowReceiver = false
                    val btn = it.view.findViewById<Button>(R.id.delete_btn)
                    btn.setOnClickListener{
                        onDelete?.invoke()
                    }
                }
    }

    private fun onUpdate(){

        if(current == null)
            return
        scene.let {
            val cameraPosition = it.camera.worldPosition
            val uiPosition: Vector3 = current!!.worldPosition
            val direction = Vector3.subtract(cameraPosition, uiPosition)
            direction.y = 0.0f
            val lookRotation = Quaternion.lookRotation(direction, Vector3.up())
            current!!.worldRotation = lookRotation
        }
    }


    override fun addInfo(parent: Node, onDelete : (() -> Unit))
    {
        if(this.parent != null)
        {
            if(this.parent == parent){
                removeInfo(parent)
                return
            }
            removeInfo(parent)
        }
        this.onDelete = onDelete
        current = Node()
        this.parent = parent
        current?.renderable = infoCard
        parent.addChild(current)
        current?.localPosition = Vector3(0f,2f,0f)
        scene.addOnUpdateListener{
            onUpdate()
        }
        
    }

    override fun removeInfo(parent: Node) {
        parent.removeChild(current)
        current?.setParent(null)
        current?.renderable = null
        current = null
        onDelete = null
        this.parent = null
        scene.removeOnUpdateListener {
            onUpdate()
        }
    }

}