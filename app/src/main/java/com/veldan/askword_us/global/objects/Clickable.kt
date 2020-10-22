package com.veldan.askword_us.global.objects

import android.view.View

object Clickable {
    fun enabled(isEnabled: Boolean, vararg views: View) {
        views.map { it.isEnabled = isEnabled }
    }
}