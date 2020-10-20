package com.veldan.askword_us

import androidx.constraintlayout.motion.widget.MotionLayout

object Animator {
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