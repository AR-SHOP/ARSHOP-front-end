package com.arthe100.arshop.scripts.mvi.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.scripts.repositories.CategoryRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val catRepo: CategoryRepository) : ViewModel(){
    private val _currentViewState =  MutableLiveData<CategoryState>(CategoryState.IdleState)
    val currentViewState : LiveData<CategoryState>
        get() = _currentViewState


    fun onEvent(action: CategoryUiAction){
        when(action){
            is CategoryUiAction.GetCategories ->{
                _currentViewState.value = CategoryState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = catRepo.getCategories()
                }
            }
        }
    }

}