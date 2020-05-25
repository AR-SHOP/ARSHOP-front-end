package com.arthe100.arshop.scripts.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arthe100.arshop.scripts.di.scopes.AppScope
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.fragments.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@MainScope
class MyFragmentFactory @Inject constructor(
    private val creator : Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        creator[loadFragmentClass(classLoader,className)]?.get() ?: super.instantiate(classLoader, className)
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class FragmentKey(val value: KClass<out Fragment>)

@Module
abstract class FragmentModule{

    @Binds
    internal abstract fun bindViewModelFactory(factory: MyFragmentFactory): FragmentFactory


    @Binds
    @IntoMap
    @FragmentKey(CategoryFragment::class)
    internal abstract fun provideCategoryFragment(fragment: CategoryFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(SignUpFragment::class)
    internal abstract fun provideSignUpFragment(fragment: SignUpFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    internal abstract fun provideHomeFragment(fragment: HomeFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    internal abstract fun provideCategoriesFragment(fragment: CategoriesFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(CartFragment::class)
    internal abstract fun provideCartFragment(fragment: CartFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    internal abstract fun provideLoginFragment(fragment: LoginFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(ProfileFragment::class)
    internal abstract fun provideProfileFragment(fragment: ProfileFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(CustomArFragment::class)
    internal abstract fun provideArFragment(fragment: CustomArFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(VerifyFragment::class)
    internal abstract fun provideVerifyFragment(fragment: VerifyFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(ProductFragment::class)
    internal abstract fun provideProductFragment(fragment: ProductFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(OrdersFragment::class)
    internal abstract fun provideOrdersFragment(fragment: OrdersFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(CustomerCartFragment::class)
    internal abstract fun provideCustomerCartFragment(fragment: CustomerCartFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(ChangePasswordFragment::class)
    internal abstract fun provideChangePasswordFragment(fragment: ChangePasswordFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(ProfileInfoFragment::class)
    internal abstract fun provideProfileInfoFragment(fragment: ProfileInfoFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(ChargeAccountFragment::class)
    internal abstract fun provideChargeAccountFragment(fragment: ChargeAccountFragment) : Fragment
    @Binds
    @IntoMap
    @FragmentKey(EditProfileInfoFragment::class)
    internal abstract fun provideEditProfileInfoFragment(fragment: EditProfileInfoFragment) : Fragment
}
