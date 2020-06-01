package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.scripts.mvi.base.HomeState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.HomeService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val service: HomeService) {
    suspend fun getHomePageSales() : ViewState{
        return try {
            val sales = service.getHomeSales()
            HomeState.HomePageSalesSuccess(sales)
        }catch (t: Throwable) { ViewState.Failure(t) }
    }

}