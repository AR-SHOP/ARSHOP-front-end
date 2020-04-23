package com.arthe100.arshop.views.atoms

import android.content.Context
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.arthe100.arshop.R
import kotlinx.android.synthetic.main.activity_main_layout.view.*

class ButtonAV (context: Context) : Atom<ButtonAV.State>() {

    private var defaultTextColor: Int = ContextCompat.getColor(context, R.color.colorWhite)
    private var defaultBackgroundColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
    var state: State = State()

    var buttonView = AppCompatButton(context).apply {
        setBackgroundColor(defaultBackgroundColor)
        setTextColor(defaultTextColor)
    }

    override fun getView(): View {
        render(state)
        return buttonView
    }

    override fun unBind() {
        state = State()
    }

    override fun render(state: State) {
        buttonView.apply {
            textSize = state.textSize
            text = state.text
            layoutParams = state.layoutParams
            setOnClickListener { state.onClick() }
        }
    }

    override fun getAtomState(): State? = state

    class State {
        var textSize: Float = 12f
        var text: String? = null
        var layoutParams: ViewGroup.LayoutParams? = null
        var onClick: () -> Unit? = { }
    }

}