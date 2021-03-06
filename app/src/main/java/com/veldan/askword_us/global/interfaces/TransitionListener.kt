package com.veldan.askword_us.global.interfaces

import androidx.constraintlayout.motion.widget.MotionLayout

interface TransitionListener : MotionLayout.TransitionListener {
    override fun onTransitionStarted(motionLayout: MotionLayout?, start: Int, end: Int) {}

    override fun onTransitionChange(motionLayout: MotionLayout?, start: Int, end: Int, progress: Float) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {}

    override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
}