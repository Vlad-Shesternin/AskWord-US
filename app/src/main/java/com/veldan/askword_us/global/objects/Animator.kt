package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout

object Animator {

    fun transition(motion: MotionLayout, end: Int, duration: Int) {
        motion.also {
            val start = it.currentState
            it.setTransition(start, end)
            it.setTransitionDuration(duration)
            it.transitionToEnd()
        }
    }
}