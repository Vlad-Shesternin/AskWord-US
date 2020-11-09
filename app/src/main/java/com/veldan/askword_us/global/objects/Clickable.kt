package com.veldan.askword_us.global.objects

import android.view.View

object Components {
    fun enabled(isEnabled: Boolean, vararg views: View) {
        views.map { it.isEnabled = isEnabled }
    }

    fun visibility(visibility: Int, vararg views: View) {
        views.map { it.visibility = visibility }
    }
}