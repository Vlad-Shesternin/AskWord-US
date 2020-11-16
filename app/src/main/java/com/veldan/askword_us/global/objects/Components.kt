package com.veldan.askword_us.global.objects

import android.graphics.drawable.Drawable
import android.view.View

object Components {
    // ==============================
    //          Enabled
    // ==============================
    fun enabled(isEnabled: Boolean, vararg views: View) {
        views.map { it.isEnabled = isEnabled }
    }

    // ==============================
    //          Visibility
    // ==============================
    fun visibility(visibility: Int, vararg views: View) {
        views.map { it.visibility = visibility }
    }

    // ==============================
    //          Background
    // ==============================
    fun background(background: Drawable?, vararg views: View) {
        views.map { it.background = background }
    }
}