package com.arthe100.arshop.views.fragments

import androidx.fragment.app.Fragment
import com.arthe100.arshop.views.BaseFragment
import javax.inject.Inject


class FragmentFactory {

    companion object {
        @Inject lateinit var homeFragment: HomeFragment
        @Inject lateinit var categoriesFragment: CategoriesFragment
        @Inject lateinit var cartFragment: CartFragment
        @Inject lateinit var profileFragment: ProfileFragment
        @Inject lateinit var loginFragment: LoginFragment
        @Inject lateinit var phoneNumberFragment: PhoneNumberFragment
        @Inject lateinit var productFragment: ProductFragment
        @Inject lateinit var signUpPasswordFragment: SignUpPasswordFragment
        @Inject lateinit var verifyFragment: VerifyFragment

        fun create(type: FragmentType) : BaseFragment {
            return when (type) {
                FragmentType.HOME -> { homeFragment }
                FragmentType.CATEGORIES -> { categoriesFragment }
                FragmentType.CART -> { cartFragment }
                FragmentType.PROFILE -> { profileFragment }
                FragmentType.LOGIN -> { loginFragment }
                FragmentType.PHONE_NUMBER -> { phoneNumberFragment }
                FragmentType.PRODUCT -> { productFragment }
                FragmentType.SIGNUP_PASSWORD -> { signUpPasswordFragment }
                FragmentType.VERIFY -> { verifyFragment }
            }
        }
    }
}
