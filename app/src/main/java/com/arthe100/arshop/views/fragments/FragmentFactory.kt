package com.arthe100.arshop.views.fragments

import androidx.fragment.app.Fragment
import com.arthe100.arshop.views.BaseFragment
import javax.inject.Inject


class FragmentFactory
    @Inject constructor(
        val homeFragment: HomeFragment,
        val categoriesFragment: CategoriesFragment,
        val cartFragment: CartFragment,
        val profileFragment: ProfileFragment,
        val loginFragment: LoginFragment,
        val phoneNumberFragment: PhoneNumberFragment,
        val productFragment: ProductFragment,
        val signUpPasswordFragment: SignUpPasswordFragment,
        val verifyFragment: VerifyFragment,
        val ordersFragment: OrdersFragment,
        val customerCartFragment: CustomerCartFragment) {

    inline fun <reified T> create() : T {
        return when(T::class) {
            HomeFragment::class -> homeFragment as T
            CategoriesFragment::class ->  categoriesFragment as T
            CartFragment::class ->  cartFragment as T
            ProfileFragment::class ->  profileFragment as T
            LoginFragment::class ->  loginFragment as T
            PhoneNumberFragment::class -> phoneNumberFragment as T
            ProductFragment::class -> productFragment as T
            SignUpPasswordFragment::class -> signUpPasswordFragment as T
            VerifyFragment::class -> verifyFragment as T
            OrdersFragment::class -> ordersFragment as T
            CustomerCartFragment::class -> customerCartFragment as T
            else -> throw Exception("the fragment of type: ${T::class} doesn't exists!" )
        }
    }
}
