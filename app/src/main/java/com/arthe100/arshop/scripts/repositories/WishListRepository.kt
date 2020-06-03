package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.mvi.base.WishListState
import com.arthe100.arshop.scripts.network.services.WishListService
import javax.inject.Inject

class WishListRepository @Inject constructor(private val service : WishListService) {

    private val TAG = WishListRepository::class.simpleName

    suspend fun getWishList(): ViewState {
        return try {
            WishListState.GetWishListSuccess(service.getWishList())

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }

    suspend fun addWishList(productId: Long): ViewState {
        return try {
            WishListState.AddWishListSuccess(service.addWishList(productId))

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }

    suspend fun deleteWishList(productId: Long): ViewState {
        return try {
            WishListState.DeleteWishListSuccess(service.deleteWishList(productId))

        }catch (t: Throwable) {
            ViewState.Failure(t)
        }
    }
}