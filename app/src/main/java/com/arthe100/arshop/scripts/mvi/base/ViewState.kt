package com.arthe100.arshop.scripts.mvi.base

import com.arthe100.arshop.models.*

sealed class ViewState{
    object IdleState                                                : ViewState()
    object LoadingState                                             : ViewState()
    data class Failure(val throwable: Throwable)                    : ViewState()
}

sealed class HomeState                                              : ViewState(){
    data class HomePageSalesSuccess(val sales: List<HomeSales>)     : HomeState()
}

sealed class ProductState                                           : ViewState(){
    data class ProductDetailSuccess(val product: Product)           : ProductState()
    data class ProductsSuccess(val products: List<Product>)         : ProductState()
}

sealed class CartState                                              : ViewState(){
    object LogoutState                                              : CartState()
    data class GetCartState(val cart: Cart)                         : CartState()
    data class AddToCartState(val cart: Cart)                       : CartState()
    data class RemoveFromCartState(val cart: Cart)                  : CartState()
    data class ClearCart(val cart: Cart)                            : CartState()
//    object PlaceOrderState : CartState()
}


sealed class AuthState                                              : ViewState(){
    object CodeSuccess                                              : AuthState()
    object SingupSuccess                                            : AuthState()
    data class LoginSuccess(val user: User.User)                    : AuthState()
}

sealed class CategoryState                                          : ViewState(){
    data class GetProductSuccess(val products: List<Product>)       : CategoryState()
    data class GetCategorySuccess(val categories: List<Category>)   : CategoryState()
}

sealed class ProfileState                                           : ViewState(){
    object LogoutState                                              : ProfileState()
    data class GetProfileSuccess(val userInfo: UserProfile)         : ProfileState()
}