package com.arthe100.arshop.views.atoms

import android.view.View

abstract class Atom<S> {
    abstract fun getView(): View
    abstract fun unBind()
    abstract fun render(state: S)
    abstract fun getAtomState(): S?
}