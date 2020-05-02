package com.arthe100.arshop.scripts.mvi.ar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthe100.arshop.scripts.network.services.ProductService
import com.arthe100.arshop.scripts.repositories.ProductRepository
import com.google.ar.sceneform.rendering.ModelRenderable
import javax.inject.Inject

class ArViewModel @Inject constructor(productRepository: ProductRepository) : ViewModel(){
    private val models : MutableMap<String , ModelRenderable> = mutableMapOf()
    var currentViewState =  MutableLiveData<ArState>(ArState.IdleState)


    fun addModel(key: String, model: ModelRenderable){
        if(models.containsKey(key)) return
        models[key] = model
    }

    fun getModel(key: String) : ModelRenderable?{
        if(!contains(key)) return null
        return models[key]
    }

    fun contains(key: String) : Boolean{
        return models.contains(key)
    }

}