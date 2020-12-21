package com.veldan.askword_us.authentication

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator

object AuthenticationAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val set_1 = R.id.set_1

    // Transitions
    private const val start_to_set_1 = R.id.start_to_set_1
    private const val set_1_to_start = R.id.set_1_to_start

    // ==============================
    //    Transitions
    // ==============================
    fun start_To_Set_1() {
        Animator.transition(motion, start_to_set_1, 1000)
    }

    fun set_1_To_Start() {
        Animator.transition(motion, set_1_to_start, 1000)
    }
}