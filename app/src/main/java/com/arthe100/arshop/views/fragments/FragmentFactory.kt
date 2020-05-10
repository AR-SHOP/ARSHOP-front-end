package com.arthe100.arshop.views.fragments

import javax.inject.Inject

class FragmentFactory

    @Inject constructor(
        val homeFragment: HomeFragment,
        val categoriesFragment: CategoriesFragment,
        val cartFragment: CartFragment,
        val profileFragment: ProfileFragment,
        val loginFragment: LoginFragment,
        val productFragment: ProductFragment,
        val verifyFragment: VerifyFragment,
        val ordersFragment: OrdersFragment,
        val customerCartFragment: CustomerCartFragment,
        val signUpFragment: SignUpFragment) {

    inline fun <reified T> create() : T {
        return when(T::class) {
            HomeFragment::class -> homeFragment as T
            CategoriesFragment::class ->  categoriesFragment as T
            CartFragment::class ->  cartFragment as T
            ProfileFragment::class ->  profileFragment as T
            LoginFragment::class ->  loginFragment as T
            ProductFragment::class -> productFragment as T
            VerifyFragment::class -> verifyFragment as T
            OrdersFragment::class -> ordersFragment as T
            CustomerCartFragment::class -> customerCartFragment as T
            SignUpFragment::class -> signUpFragment as T
            else -> throw Exception("the fragment of type: ${T::class} doesn't exists!" )
        }
    }
}
