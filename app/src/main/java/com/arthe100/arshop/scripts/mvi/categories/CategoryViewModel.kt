package com.arthe100.arshop.scripts.mvi.categories

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.CurrentCategory
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.CategoryRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val catRepo: CategoryRepository) : ViewModelBase(){

    lateinit var currentCategory: CurrentCategory
    private lateinit var tempCategory: Category

    var categories: List<Category>? = null

    override fun onEvent(action: UiAction){
        when(action){
            is CategoryUiAction.GetCategories ->{

                if(categories != null)
                    _currentViewState.value = CategoryState.GetCategorySuccess(categories!!)
                else
                    _currentViewState.value = ViewState.LoadingState
                viewModelScope.launch {
                    _currentViewState.value = catRepo.getCategories()
                    _currentViewState.value = ViewState.IdleState
                }
            }

            is CategoryUiAction.GetCategoryProduct -> {
                viewModelScope.launch {
                    tempCategory = action.category
                    _currentViewState.value = catRepo.getProducts(action.category.id)
                    _currentViewState.value = ViewState.IdleState
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