package com.arthe100.arshop.scripts.ar.InfoManager

import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3

interface IInfoManager {

    fun init(scene: Scene)

    fun addInfo(parent: Node, onDelete : (() -> Unit))

    fun removeInfo(parent: Node)

}