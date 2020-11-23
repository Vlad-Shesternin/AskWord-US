package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout

object Animator {
    var previous = 0

    fun transitionToEnd(motion: MotionLayout, transitionId: Int, duration: Int) {
        motion.also {
            it.setTransition(transitionId)
            it.setTransitionDuration(duration)
            it.transitionToEnd()
        }
    }

    fun transitionToStart(motion: MotionLayout, transitionId: Int, duration: Int) {
        motion.also {
            it.setTransition(transitionId)
            it.setTransitionDuration(duration)
            it.transitionToStart()
        }
    }
}