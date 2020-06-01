package com.arthe100.arshop.scripts.mvi.home

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repo: HomeRepository
) : ViewModelBase() , ICacheLoader{

    var currentSales: List<HomeSales>? = null


    override fun onEvent(action: UiAction) {
        when(action){
            HomeUiAction.GetHomePageSales -> {
                viewModelScope.launch {
                    _currentViewState.value = repo.getHomePageSales()
                    _currentViewState.value = ViewState.IdleState
                }
            }
        }
    }

    override fun load(func: (ViewState) -> Unit) {
        if(currentSales != null)
            func(HomeState.HomePageSalesSuccess(currentSales!!))
    }

}