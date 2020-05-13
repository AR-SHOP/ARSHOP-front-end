package com.arthe100.arshop.scripts.di.modules

import androidx.fragment.app.FragmentActivity
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.fragments.*
import dagger.Module
import dagger.Provides

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

    @JvmStatic
    @MainScope
    @Provides
    fun provideDialogBox() : DialogBoxManager {
        return DialogBoxManager()
    }
}
