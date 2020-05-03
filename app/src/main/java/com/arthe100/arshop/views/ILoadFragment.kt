package com.arthe100.arshop.views

import androidx.fragment.app.Fragment

enum class LoadFragmentType { LOAD, RETURN } //this is for the animation types

interface ILoadFragment {
    fun loadFragment(fragment: Fragment?, type: LoadFragmentType = LoadFragmentType.LOAD)
}