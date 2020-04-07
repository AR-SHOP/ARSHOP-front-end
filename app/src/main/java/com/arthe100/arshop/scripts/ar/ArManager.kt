package com.arthe100.arshop.scripts.ar

import android.content.Context
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.PlaneRenderer
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import java.lang.NullPointerException
import java.util.logging.Logger


class ArManager(fragment: ArFragment) {
private val TAG = ArManager::class.simpleName

    private val models : MutableMap<String , Renderable> = mutableMapOf()
    private var currentUri : String = ""
    private var context : Context? = null


    init {
        val sceneView = fragment.arSceneView
        sceneView.scene.addOnUpdateListener{
            fragment.onUpdate(it)

            val plane = sceneView.planeRenderer

            plane.material.thenAccept{mat ->
                mat.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS , 100f)
                mat.setFloat3(PlaneRenderer.MATERIAL_COLOR , Color(0f, 255f, 165f))
            }
        }

        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if(plane.isPoseInExtents(hitResult.hitPose))
                setModel(currentUri , fragment , hitResult.createAnchor())
        }
    }


    public fun setContext(context: Context)
    {
        this.context = context
    }
    public fun setUri(uri: String)
    {
        this.currentUri = uri
    }




    public fun setModel(uri: String , fragment : ArFragment , anchor: Anchor){
        ModelRenderable.Builder()
                .setSource(context , RenderableSource.Builder().setSource(
                        context,
                        Uri.parse(uri),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.5f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(uri)
                .build()
                .thenAccept{
                    placeModel(fragment , it , anchor)
                }
                .exceptionally {
                    showMSG("Unable to load renderable $uri")
                    return@exceptionally null
                }
    }

    fun placeModel(fragment : ArFragment , model : ModelRenderable , anchor: Anchor){
        if(model == null){
            Log.e(TAG , "model is not loaded!")
            return
        }

        val anchorNode = AnchorNode(anchor)
        anchorNode.renderable = model
        fragment.arSceneView.scene.addChild(anchorNode)

    }


    fun showMSG(msg : String){

        if(context == null){
            Log.e(TAG , "context can't be null!")
            return
        }

        val toast = Toast.makeText(context, msg , Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


}