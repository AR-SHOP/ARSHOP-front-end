package com.arthe100.arshop.scripts.mvi.base

import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Comment
import com.arthe100.arshop.models.CommentNetwork
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.mviBase.Action

sealed class UiAction

sealed class HomeUiAction                                               : UiAction(){
    object GetHomePageSales                                             : HomeUiAction()
}

sealed class ProductUiAction                                            : UiAction(){
    object GetProducts                                                  : ProductUiAction()
    data class SendCommentAction(val comment: CommentNetwork)                  : ProductUiAction()
    data class GetProductDetails(val product: Product)                  : ProductUiAction()
}


sealed class CartUiAction                                               : UiAction(){
    object GetCart                                                      : CartUiAction()
    object ClearCart                                                    : CartUiAction()
    object GetCartOnStart                                               : CartUiAction()
    object GetCartInBackground                                          : CartUiAction()
    data class RemoveFromCart(val id: Long)                             : CartUiAction()
    data class AddToCart(val id: Long , val quantity: Int)              : CartUiAction()
    data class DecreaseQuantity(val id: Long , val offset: Int)         : CartUiAction()
    data class IncreaseQuantity(val id: Long , val offset: Int)         : CartUiAction()
    //data class PlaceOrder() : CartUiAction()
}


sealed class AuthUiAction                                               : UiAction(){
    object LogoutAction                                                 : AuthUiAction()
    data class CheckCodeAction(val code: String)                        : AuthUiAction()
    data class SignupAction(val password: String , val phone: String)   : AuthUiAction()
    data class LoginAction(val password: String , val phone: String)    : AuthUiAction()
}

sealed class CategoryUiAction                                           : UiAction(){
    object GetCategories                                                : CategoryUiAction()
    data class GetCategoryProduct(val category: Category)               : CategoryUiAction()
}


sealed class ProfileUiAction                                            : UiAction(){
    object LogoutAction                                                 : ProfileUiAction()
    object GetHomePageProfileAction                                     : ProfileUiAction()
}