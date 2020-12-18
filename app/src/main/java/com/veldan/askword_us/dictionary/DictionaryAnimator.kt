package com.veldan.askword_us.dictionary

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator

object DictionaryAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val set_1 = R.id.set_1
    const val set_2 = R.id.set_2

    // TransitionIds
    private const val start_To_set_1 = R.id.start_to_set_1
    private const val set_1_To_set_2 = R.id.set_1_to_set_2
    private const val set_2_To_set_3 = R.id.set_2_to_set_3
    private const val set_2_To_set_4 = R.id.set_2_to_set_4
    private const val set_2_To_set_5 = R.id.set_2_to_set_5
    private const val start_To_set_6 = R.id.start_to_set_6
    private const val set_6_To_set_7 = R.id.set_6_to_set_7


    // ==============================
    //    transitionToEnd
    // ==============================
    fun start_To_Set_1(motion: MotionLayout) {
        Animator.transition(motion, start_To_set_1, 1000)
    }

    fun set_1_To_Set_2() {
        Animator.transition(motion, set_1_To_set_2, 1000)
    }

    fun set_2_To_Set_3() {
        Animator.transition(motion, set_2_To_set_3, 1000)
    }

    fun set_2_To_Set_4() {
        Animator.transition(motion, set_2_To_set_4, 1000)
    }

    fun set_2_To_Set_5() {
        Animator.transition(motion, set_2_To_set_5, 1000)
    }

    fun start_To_Set_6() {
        Animator.transition(motion, start_To_set_6, 1000)
    }

    fun set_6_To_set_7() {
        Animator.transition(motion, set_6_To_set_7, 1000)
    }

    // ==============================
    //    transitionToStart
    // ==============================
    fun set_1_To_Start() {
        Animator.transition(motion, start_To_set_1, 1000)
    }

    fun set_2_To_Set_1() {
        Animator.transition(motion, set_1_To_set_2, 1000)
    }

    fun set_7_To_set_6() {
        Animator.transition(motion, set_6_To_set_7, 1000)
    }
}