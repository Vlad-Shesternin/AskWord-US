package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout

enum class Direction {
    TO_START, TO_END
}

object Animator2 {
    fun transition(
        motion: MotionLayout,
        startId: Int,
        endId: Int,
        duration: Int = 1000,
        direction: Direction,
    ) {
        motion.also {
            it.setTransition(startId, endId)
            it.setTransitionDuration(duration)
            when (direction) {
                Direction.TO_END -> it.transitionToEnd()
                Direction.TO_START -> it.transitionToStart()
            }
        }
    }
}