package com.arthe100.arshop.scripts.di.modules

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.Adapters.ViewPagerAdapter
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
        productFragment: ProductFragment,
        verifyFragment: VerifyFragment,
        ordersFragment: OrdersFragment,
        customerCartFragment: CustomerCartFragment,
        signUpFragment: SignUpFragment) : FragmentFactory {

        return FragmentFactory(homeFragment, categoriesFragment, cartFragment, profileFragment,
                                loginFragment, productFragment, verifyFragment, ordersFragment,
                                customerCartFragment, signUpFragment)
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createSignUpFragment() : SignUpFragment {
        return SignUpFragment()
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
    fun provideVerifyFragment() : VerifyFragment {
        return VerifyFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideProductFragment() : ProductFragment {
        return ProductFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createOrdersFragment() : OrdersFragment {
        return OrdersFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createCustomerCartFragment() : CustomerCartFragment {
        return CustomerCartFragment()
    }
}
