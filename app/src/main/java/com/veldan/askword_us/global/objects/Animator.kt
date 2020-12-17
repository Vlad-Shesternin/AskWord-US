package com.veldan.askword_us.global.objects

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.global.emums.Direction
import com.veldan.askword_us.global.emums.Direction.toEND
import com.veldan.askword_us.global.emums.Direction.toSTART

object Animator {
    var previous = 0

    fun transition(
        motion: MotionLayout,
        transitionId: Int,
        duration: Int,
        direction: Direction
    ) {
        motion.also {
            it.setTransition(transitionId)
            it.setTransitionDuration(duration)
            when (direction) {
                toEND -> it.transitionToEnd()
                toSTART -> it.transitionToStart()
            }
        }
    }
}
