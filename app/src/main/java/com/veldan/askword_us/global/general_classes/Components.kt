package com.veldan.askword_us.global.general_classes

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import com.veldan.askword_us.R

class Components(vararg views: View) {
    private val views = views

    // ==============================
    //          Enabled
    // ==============================
    fun enabled(isEnabled: Boolean): Components {
        views.map { it.isEnabled = isEnabled }
        return this
    }

    // ==============================
    //          Background
    // ==============================
    fun background(background: Drawable?): Components {
        views.map { it.background = background }
        return this
    }
}