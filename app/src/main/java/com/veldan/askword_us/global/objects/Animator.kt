package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout

object Animator {
    const val NO_INTERNET = "no_internet.json"
    const val LOADING = "loading_among_us.json"

    fun transition(motion: MotionLayout, start: Int, end: Int, duration: Int) {
        motion.also {
            if (it.currentState == start) {
                it.setTransition(start, end)
                it.setTransitionDuration(duration)
                it.transitionToEnd()
            }
        }
    }
}