package com.arthe100.arshop.views.interfaces

import androidx.fragment.app.Fragment

interface ILoadFragment {
    fun loadFragment(klass: Class<out Fragment>)
}