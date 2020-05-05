package com.arthe100.arshop.scripts.di.modules

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.fragments.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
object MainModule{

    @JvmStatic
    @MainScope
    @Provides
    fun provideFragmentFactory(
        homeFragment: HomeFragment,
        categoriesFragment: CategoriesFragment,
        cartFragment: CartFragment,
        profileFragment: ProfileFragment,
        loginFragment: LoginFragment,
        phoneNumberFragment: PhoneNumberFragment,
        productFragment: ProductFragment,
        signUpPasswordFragment: SignUpPasswordFragment,
        verifyFragment: VerifyFragment) : FragmentFactory {

        return FragmentFactory(homeFragment, categoriesFragment, cartFragment, profileFragment,
                                loginFragment, phoneNumberFragment, productFragment,
                                signUpPasswordFragment, verifyFragment)
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createHomeFragment() : HomeFragment {
        return HomeFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createCategoriesFragment() : CategoriesFragment {
        return CategoriesFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createCartFragment() : CartFragment {
        return CartFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createLoginFragment() : LoginFragment {
        return LoginFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createProfileFragment() : ProfileFragment {
        return ProfileFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideArFragment() : CustomArFragment {
        return CustomArFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun providePhoneNumberFragment() : PhoneNumberFragment {
        return PhoneNumberFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideVerifyFragment() : VerifyFragment {
        return VerifyFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideSignUpPasswordFragment() : SignUpPasswordFragment {
        return SignUpPasswordFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideProductFragment() : ProductFragment {
        return ProductFragment()
    }
}
