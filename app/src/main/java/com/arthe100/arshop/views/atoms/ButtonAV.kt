package com.arthe100.arshop.views.atoms

import android.content.Context
import android.text.Layout
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.marginTop
import com.arthe100.arshop.R
import kotlinx.android.synthetic.main.activity_main_layout.view.*

class ButtonAV (val context: Context) {

    var button: Button = Button(context)
        private set

    fun make() : Button {
        return button
    }
}