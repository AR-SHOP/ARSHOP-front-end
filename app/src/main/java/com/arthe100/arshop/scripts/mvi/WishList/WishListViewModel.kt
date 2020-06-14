package com.arthe100.arshop.scripts.mvi.WishList

import androidx.lifecycle.viewModelScope
import com.arthe100.arshop.models.WishList
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.repositories.WishListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WishListViewModel @Inject constructor(private val wishListRepository: WishListRepository) : ViewModelBase() {

    var currentWishList: WishList? = null

    override fun onEvent(action: UiAction) {
        when (action) {
            is WishListUiAction.GetWishListAction -> {
                if (currentWishList != null)
                    _currentViewState.value = WishListState.GetWishListSuccess(currentWishList!!)
                else
                    _currentViewState.value = ViewState.LoadingState

                viewModelScope.launch {
                    _currentViewState.value = wishListRepository.getWishList()
                }
            }
            is WishListUiAction.AddWishListAction -> {
                if (currentWishList != null)
                    _currentViewState.value = WishListState.AddWishListSuccess(currentWishList!!)
                else
                    _currentViewState.value = ViewState.LoadingState


                viewModelScope.launch {
                    _currentViewState.value = wishListRepository.addWishList(action.wishListProductID)
                }
            }
            is WishListUiAction.DeleteWishListAction -> {
                if (currentWishList != null)
                    _currentViewState.value = WishListState.DeleteWishListSuccess(currentWishList!!)
                else
                    _currentViewState.value = ViewState.LoadingState


                viewModelScope.launch {
                    _currentViewState.value = wishListRepository.deleteWishList(action.wishListProductID)
                }
            }
        }
    }
}