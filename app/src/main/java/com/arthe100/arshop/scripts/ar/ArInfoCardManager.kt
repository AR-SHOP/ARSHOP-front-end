package com.arthe100.arshop.scripts.ar

import android.content.Context
import android.widget.Button
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.messege.MessageManager
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import javax.inject.Inject


class ArInfoCardManager (context : Context, layoutId : Int){

    private lateinit var infoCard : ViewRenderable
    private var parent : Node? = null
    private var current : Node? = null
    private var callback : (() -> Unit)? = null



    init {
        ViewRenderable.builder()
                .setView(context , layoutId)
                .build()
                .thenAccept{
                    infoCard = it
                    it.isShadowCaster = false
                    it.isShadowReceiver = false
                    val btn = it.view.findViewById<Button>(R.id.btnDelete)
                    btn.setOnClickListener{
                        callback?.invoke()
                    }
                }
    }


    fun addInfoOn(parent: Node , callback : (() -> Unit))
    {
        if(this.parent != null)
        {
            if(this.parent == parent){
                removeInfoFrom(parent)
                return
            }
            removeInfoFrom(parent)
        }
        this.callback = callback
        current = Node()
        this.parent = parent
        current?.renderable = infoCard
        parent.addChild(current)
        current?.localPosition = Vector3(0f,1f,0f)
//        current?.localScale = Vector3(2f,.5f,1f)
    }

    fun removeInfoFrom(parent: Node) {
        parent.removeChild(current)
        current?.setParent(null)
        current?.renderable = null
        current = null
        callback = null
        this.parent = null
    }

}