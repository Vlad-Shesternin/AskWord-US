package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout

object Animator {

    fun transition(
        motion: MotionLayout,
        transitionId: Int,
        duration: Int,
    ) {
        motion.also {
            it.setTransition(transitionId)
            it.setTransitionDuration(duration)
            it.transitionToEnd()
        }
    }
}
