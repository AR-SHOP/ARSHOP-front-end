package com.arthe100.arshop.scripts.mvi.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.CurrentCategory
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.repositories.CategoryRepository
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val catRepo: CategoryRepository) : ViewModel(){
    private val _currentViewState =  MutableLiveData<CategoryState>(CategoryState.IdleState)
    val currentViewState : LiveData<CategoryState>
        get() = _currentViewState

    lateinit var currentCategory: CurrentCategory
    private lateinit var tempCategory: Category

    fun onEvent(action: CategoryUiAction){
        when(action){
            is CategoryUiAction.GetCategories ->{
                _currentViewState.value = CategoryState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = catRepo.getCategories()
                    _currentViewState.value = CategoryState.IdleState
                }
            }

            is CategoryUiAction.GetCategoryProduct -> {
                viewModelScope.launch {
                    tempCategory = action.category
                    _currentViewState.value = catRepo.getProducts(action.category.id)
                    _currentViewState.value = CategoryState.IdleState
                }
            }
        }
    }

    fun setProducts(products: List<Product>){
        if(!this::tempCategory.isInitialized)
            throw NullPointerException("tempCategory can't be null at this state!")
        currentCategory = CurrentCategory(products,tempCategory)
    }

}