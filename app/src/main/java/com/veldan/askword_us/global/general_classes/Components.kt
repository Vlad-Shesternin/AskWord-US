package com.veldan.askword_us.global.general_classes

import android.graphics.drawable.Drawable
import android.view.View

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
    //          Visibility
    // ==============================
    fun visibility(visibility: Int): Components {
        views.map { it.visibility = visibility }
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