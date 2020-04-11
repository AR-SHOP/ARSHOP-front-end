package com.arthe100.arshop.views.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.CustomBaseArFragment
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.PlaneRenderer
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.ar_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject


class CustomArFragment : CustomBaseArFragment() {

    public val tableUrl = "https://poly.googleapis.com/downloads/fp/1586167353776716/8cnrwlAWqx7/cfVCFxWqtbc/Table_Large_Rectangular_01.gltf"
    public val duckUrl = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
    public val bedUrl = "https://poly.googleapis.com/downloads/fp/1586167422468753/8mkAgVYGbL4/5oNDqZI-I0J/Bed_01.gltf"

//    override fun onStart() {
//        table_btn.setOnClickListener {
//            setUri(tableUrl)
//        }
//        duck_btn.setOnClickListener {
//            setUri(duckUrl)
//        }
//        bed_btn.setOnClickListener {
//            setUri(bedUrl)
//        }
//        super.onStart()
//    }

    override fun inject() {
        (activity?.application as BaseApplication)
                .mainComponent(activity!!)
                .arComponent().create(arSceneView.scene).inject(this)
    }

    private val TAG = CustomArFragment::class.simpleName
    private val models : MutableMap<String , ModelRenderable> = mutableMapOf()
    private var currentUri : String = ""

    @Inject lateinit var arInfoCardManager : IInfoManager
    @Inject lateinit var messageManager : MessageManager


//    init {
//        setUri(tableUrl)
//    }

    private fun init() {

        val sceneView = this.arSceneView
//        sceneView.planeRenderer.material.thenAccept{
//            it.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS , 4f)
//            it.setFloat3(PlaneRenderer.MATERIAL_COLOR , Color(0f, 255f, 165f))
//
//        }

        sceneView.scene.addOnUpdateListener{
            this.onUpdate(it)
            val plane = sceneView.planeRenderer

            plane.material.thenAccept{mat ->
                mat.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS , 4f)
                mat.setFloat3(PlaneRenderer.MATERIAL_COLOR , Color(0f, 255f, 165f))
            }

        }

        this.setOnTapArPlaneListener { hitResult, plane, _ ->
            if(plane.isPoseInExtents(hitResult.hitPose) && plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING)
                setModel(currentUri , hitResult.createAnchor())
        }

        arInfoCardManager.init()
//        arInfoCardManager = BasicArInfoCardManager(activity!!)

    }

    override fun getSessionConfiguration(session: Session?): Config {
        Log.d("CustomArFragment", "ABCD before session$arSceneView" )
        val conf = super.getSessionConfiguration(session)
        Log.d("CustomArFragment", "ABCD after session$arSceneView" )
        init()

        return conf
    }


    fun showInfo(parent : Node , root : Node){
        arInfoCardManager.addInfo(parent) {
            root.renderable = null
            root.removeChild(parent) }

        messageManager.toast(context!! , "model hit!")
    }


    fun hideInfo(parent: Node) {
        arInfoCardManager.removeInfo(parent)
    }

    fun setUri(uri: String) {
        this.currentUri = uri
    }

    private fun setModel(uri: String , anchor: Anchor){

        if(models.containsKey(uri))
        {
            placeModel(models[uri]!! , anchor)
            return
        }

        ModelRenderable.Builder()
                .setSource(activity , RenderableSource.Builder().setSource(
                        activity,
                        Uri.parse(uri),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.1f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(uri)
                .build()
                .thenAccept{

                    models[uri] = it
                    placeModel(it , anchor)
                }
                .exceptionally {
                    messageManager.toast(context!!, "Unable to load renderable $uri")
                    return@exceptionally null
                }
    }

    private fun placeModel(model : ModelRenderable, anchor: Anchor){

        val anchorNode = AnchorNode(anchor)
        this.arSceneView.scene.addChild(anchorNode)

        val node = TransformableNode(this.transformationSystem)
        anchorNode.addChild(node)
        node.renderable = model
        node.localScale = Vector3(0.1f , 0.1f , 0.1f)
        node.setOnTapListener{ _, _ -> showInfo(node , anchorNode) }
    }

}


