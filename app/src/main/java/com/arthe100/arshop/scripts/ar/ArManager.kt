package com.arthe100.arshop.scripts.ar

import android.content.Context
import android.net.Uri
import android.util.Log
import com.arthe100.arshop.scripts.messege.MessageManager
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import javax.inject.Inject


class ArManager @Inject constructor(context : Context , fragment: ArFragment , infoCardLayoutId : Int , messageManager: MessageManager) {


    private val TAG = ArManager::class.simpleName
    private val models : MutableMap<String , ModelRenderable> = mutableMapOf()
    private var arInfoCardManager : ArInfoCardManager
    private var currentUri : String = ""
    private val context : Context

    val messageManager : MessageManager


    init {
        val sceneView = fragment.arSceneView
        sceneView.scene.addOnUpdateListener{
            fragment.onUpdate(it)
            val plane = sceneView.planeRenderer

            plane.material.thenAccept{mat ->
                mat.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS , 5f)
                mat.setFloat3(PlaneRenderer.MATERIAL_COLOR , Color(0f, 255f, 165f))
            }

        }

        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if(plane.isPoseInExtents(hitResult.hitPose))
                setModel(currentUri , fragment , hitResult.createAnchor())
        }

        arInfoCardManager = ArInfoCardManager(context , infoCardLayoutId)
        this.messageManager = messageManager
        this.context = context

    }

    fun showInfo(parent : Node , root : Node){
        arInfoCardManager.addInfoOn(parent
        ) {
            root.renderable = null
            root.removeChild(parent) }

        messageManager.toast(context , "model hit!")
    }

    fun hideInfo(parent: Node){
        arInfoCardManager.removeInfoFrom(parent)
    }

    fun setUri(uri: String)
    {
        this.currentUri = uri
    }

    private fun setModel(uri: String, fragment : ArFragment, anchor: Anchor){

        if(models.containsKey(uri))
        {
            placeModel(fragment , models[uri]!! , anchor)
            return
        }

        ModelRenderable.Builder()
                .setSource(context , RenderableSource.Builder().setSource(
                        context,
                        Uri.parse(uri),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.1f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(uri)
                .build()
                .thenAccept{

                    models[uri] = it
                    placeModel(fragment , it , anchor)
                }
                .exceptionally {
                    messageManager.toast(context, "Unable to load renderable $uri")
                    return@exceptionally null
                }
    }

    private fun placeModel(fragment : ArFragment, model : ModelRenderable, anchor: Anchor){

        val anchorNode = AnchorNode(anchor)
        fragment.arSceneView.scene.addChild(anchorNode)

        val node = TransformableNode(fragment.transformationSystem)
        anchorNode.addChild(node)
        node.renderable = model
        node.localScale = Vector3(0.1f , 0.1f , 0.1f)
        node.setOnTapListener{ _, _ -> showInfo(node , anchorNode) }
    }
}


