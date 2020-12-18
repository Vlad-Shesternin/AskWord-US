package com.veldan.askword_us.dictionary.word_creator

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator

object WordCreatorAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val set_1 = R.id.set_1
    const val set_2 = R.id.set_2
    const val set_3 = R.id.set_3
    const val set_4 = R.id.set_4
    const val set_5 = R.id.set_5

    // TransitionIds
    private const val start_to_set_1 = R.id.start_to_set_1
    private const val start_to_set_3 = R.id.start_to_set_3
    private const val set_1_to_start = R.id.set_1_to_start
    private const val set_1_to_set_2 = R.id.set_1_to_set_2
    private const val set_1_to_set_3 = R.id.set_1_to_set_3
    private const val set_2_to_set_1 = R.id.set_2_to_set_1
    private const val set_3_to_start = R.id.set_3_to_start
    private const val set_3_to_set_1 = R.id.set_3_to_set_1
    private const val set_3_to_set_4 = R.id.set_3_to_set_4
    private const val set_3_to_set_5 = R.id.set_3_to_set_5
    private const val set_5_to_set_3 = R.id.set_5_to_set_3


    // ==============================
    //    transitions
    // ==============================
    fun start_To_Set_1() {
        Animator.transition(motion, start_to_set_1, 1000)
    }

    fun start_To_Set_3() {
        Animator.transition(motion, start_to_set_3, 300)
    }

    fun set_1_To_Start() {
        Animator.transition(motion, set_1_to_start, 1000)
    }

    fun set_1_To_Set_2() {
        Animator.transition(motion, set_1_to_set_2, 1000)
    }

    fun set_1_To_Set_3() {
        Animator.transition(motion, set_1_to_set_3, 1000)
    }

    fun set_2_To_Set_1() {
        Animator.transition(motion, set_2_to_set_1, 1000)
    }

    fun set_3_To_Start() {
        Animator.transition(motion, set_3_to_start, 300)
    }

    fun set_3_To_Set_1() {
        Animator.transition(motion, set_3_to_set_1, 1000)
    }

    fun set_3_To_Set_4() {
        Animator.transition(motion, set_3_to_set_4, 300)
    }

    fun set_3_To_Set_5() {
        Animator.transition(motion, set_3_to_set_5, 1000)
    }

    fun set_5_To_Set_3() {
        Animator.transition(motion, set_5_to_set_3, 1000)
    }
}