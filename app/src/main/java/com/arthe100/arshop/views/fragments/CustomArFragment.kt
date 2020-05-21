package com.arthe100.arshop.views.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.ar.InfoManager.IInfoManager
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.ar.ArState
import com.arthe100.arshop.scripts.mvi.ar.ArViewModel
import com.arthe100.arshop.views.CustomBaseArFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
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
import kotlinx.android.synthetic.main.activity_main_layout.*
import javax.inject.Inject

class CustomArFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val arInfoCardManager : IInfoManager,
    private val messageManager : MessageManager,
    private val dialogBox: DialogBoxManager
) : CustomBaseArFragment() {
//    public val tableUrl = "https://poly.googleapis.com/downloads/fp/1586167353776716/8cnrwlAWqx7/cfVCFxWqtbc/Table_Large_Rectangular_01.gltf"
//    public val duckUrl = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF/Duck.gltf"
//    public val bedUrl = "https://poly.googleapis.com/downloads/fp/1586167422468753/8mkAgVYGbL4/5oNDqZI-I0J/Bed_01.gltf"



    private val TAG = CustomArFragment::class.simpleName
    private lateinit var model: ArViewModel
    private val observer = Observer(::render)

    override fun inject() {
        (activity?.application as BaseApplication)
                .mainComponent(requireActivity())
                .arComponent().create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ar_fragment_layout , container , false)
        val arFrame = view.findViewById<FrameLayout>(R.id.ar_container)
        val v = super.onCreateView(inflater, arFrame, savedInstanceState)
        Log.d("abcd" , "$v")
        (view as ViewGroup).addView(v)
        return view
    }

    override fun onStart() {
        requireActivity().bottom_navbar.visibility = View.GONE
        super.onStart()
    }

    override fun toString(): String {
        return "AR Fragment"
    }

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
                setModel(model.currentUri , hitResult.createAnchor())
        }

        arInfoCardManager.init(sceneView.scene)
    }

    override fun getSessionConfiguration(session: Session?): Config {
        val conf = super.getSessionConfiguration(session)
        init()
        return conf
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ArViewModel::class.java)
        model.currentViewState.observe(requireActivity(), observer)
    }

    private fun showInfo(parent : Node , root : Node){
        arInfoCardManager.addInfo(parent) {
            root.renderable = null
            root.removeChild(parent) }

        messageManager.toast(requireContext() , "model hit!")
    }


    private fun setModel(uri: String , anchor: Anchor){

        if(model.currentViewState.value != ArState.IdleState){
            messageManager.toast(requireContext() , "A process already running!")
            return
        }

        model.currentViewState.value = ArState.LoadingState

        if(model.contains(uri))
        {
            model.currentViewState.value = ArState.ModelSuccess(uri , model.getModel(uri)!! , anchor)
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
                    model.currentViewState.value = ArState.ModelSuccess(uri , it , anchor)
                }
                .exceptionally {
                    model.currentViewState.value = ArState.ModelFailure(it)
                    return@exceptionally null
                }
    }

    private fun render(state: ArState){
        when(state)
        {
            is ArState.IdleState -> {
//                view?.findViewById<ConstraintLayout>(R.id.loading_bar)?.visibility = View.INVISIBLE
                dialogBox.cancel()
            }
            is ArState.ModelSuccess ->{
                dialogBox.cancel()
                model.currentViewState.value = ArState.IdleState
                model.addModel(state.uri , state.model)
                placeModel(state.model , state.anchor)
            }
            is ArState.LoadingState -> {
//                view?.findViewById<ConstraintLayout>(R.id.loading_bar)?.visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(),MessageType.LOAD)
            }
            is ArState.ModelFailure -> {
                model.currentViewState.value = ArState.IdleState
                dialogBox.showDialog(requireContext(), MessageType.ERROR)
                Log.v("TAG", state.err.toString())
            }
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
