package com.arthe100.arshop.scripts.di.modules

import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.fragments.PhoneNumberFragment
import com.arthe100.arshop.views.fragments.SignUpPasswordFragment
import com.arthe100.arshop.views.fragments.VerifyFragment
import dagger.Module
import dagger.Provides

@Module
object MainModule{

    @JvmStatic
    @MainScope
    @Provides
    fun createArFragment() : CustomArFragment {
        return CustomArFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createPhoneNumberFragment() : PhoneNumberFragment {
        return PhoneNumberFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createVerifyFragment() : VerifyFragment {
        return VerifyFragment()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun createSignUpPasswordFragment() : SignUpPasswordFragment {
        return SignUpPasswordFragment()
    }

}
