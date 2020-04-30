package com.arthe100.arshop.scripts.di.modules

import android.content.Context
import androidx.preference.PreferenceManager
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.scopes.MainScope
import com.arthe100.arshop.views.fragments.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
object MainModule{


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

    @JvmStatic
    @MainScope
    @Provides
    fun provideProfileFragment() : ProfileFragment {
        return ProfileFragment()
    }

}
