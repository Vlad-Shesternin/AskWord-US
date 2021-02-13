package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.global.objects.Direction.*

typealias StartEnd = Pair<Int, Int>

enum class Direction {
    TO_START, TO_END
}

object Animator2 {
    fun transition(
        motion: MotionLayout,
        start_end: StartEnd,
        duration: Int,
        direction: Direction,
    ) {
        motion.apply {
            setTransition(start_end.first, start_end.second)
            setTransitionDuration(duration)
            when (direction) {
                TO_END -> transitionToEnd()
                TO_START -> transitionToStart()
            }
        }
    }
}