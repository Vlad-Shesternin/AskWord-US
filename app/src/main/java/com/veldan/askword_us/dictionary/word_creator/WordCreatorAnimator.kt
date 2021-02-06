package com.veldan.askword_us.dictionary.word_creator

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator

object WordCreatorAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout
    var trigger = 0

    // States
    const val start = R.id.start
    const val set_1 = R.id.set_1
    const val set_2 = R.id.set_2
    const val set_3 = R.id.set_3
    const val set_4 = R.id.set_4
    const val set_5 = R.id.set_5
    const val set_6 = R.id.set_6
    const val set_7 = R.id.set_7
    const val set_8 = R.id.set_8
    const val set_9 = R.id.set_9
    const val set_10 = R.id.set_10
    const val set_11 = R.id.set_11
    const val set_12 = R.id.set_12
    const val set_13 = R.id.set_13
    const val set_14 = R.id.set_14
    const val set_15 = R.id.set_15
    const val set_16 = R.id.set_16
    const val set_17 = R.id.set_17

    // TransitionIds
    private const val start_to_set_1 = R.id.start_to_set_1
    private const val start_to_set_3 = R.id.start_to_set_3
    private const val start_to_set_6 = R.id.start_to_set_6
    private const val start_to_set_7 = R.id.start_to_set_7
    private const val set_1_to_start = R.id.set_1_to_start
    private const val set_1_to_set_2 = R.id.set_1_to_set_2
    private const val set_1_to_set_3 = R.id.set_1_to_set_3
    private const val set_2_to_set_1 = R.id.set_2_to_set_1
    private const val set_2_to_set_16 = R.id.set_2_to_set_16
    private const val set_3_to_start = R.id.set_3_to_start
    private const val set_3_to_set_1 = R.id.set_3_to_set_1
    private const val set_3_to_set_4 = R.id.set_3_to_set_4
    private const val set_3_to_set_5 = R.id.set_3_to_set_5
    private const val set_3_to_set_6 = R.id.set_3_to_set_6
    private const val set_3_to_set_8 = R.id.set_3_to_set_8
    private const val set_5_to_set_3 = R.id.set_5_to_set_3
    private const val set_7_to_start = R.id.set_7_to_start
    private const val set_7_to_set_9 = R.id.set_7_to_set_9
    private const val set_8_to_set_3 = R.id.set_8_to_set_3
    private const val set_8_to_set_9 = R.id.set_8_to_set_9
    private const val set_9_to_set_7 = R.id.set_9_to_set_7
    private const val set_9_to_set_8 = R.id.set_9_to_set_8
    private const val set_9_to_set_10 = R.id.set_9_to_set_10
    private const val set_9_to_set_11 = R.id.set_9_to_set_11
    private const val set_10_to_set_11 = R.id.set_10_to_set_11
    private const val set_10_to_set_14 = R.id.set_10_to_set_14
    private const val set_11_to_set_10 = R.id.set_11_to_set_10
    private const val set_11_to_set_12 = R.id.set_11_to_set_12
    private const val set_11_to_set_13 = R.id.set_11_to_set_13
    private const val set_11_to_set_14 = R.id.set_11_to_set_14
    private const val set_13_to_set_11 = R.id.set_13_to_set_11
    private const val set_14_to_set_10 = R.id.set_14_to_set_10
    private const val set_14_to_set_11 = R.id.set_14_to_set_11
    private const val set_14_to_set_15 = R.id.set_14_to_set_15
    private const val set_15_to_set_14 = R.id.set_15_to_set_14
    private const val set_15_to_set_17 = R.id.set_15_to_set_17
    private const val set_16_to_set_2 = R.id.set_16_to_set_2
    private const val set_17_to_set_15 = R.id.set_17_to_set_15


    // ==============================
    //    transitions
    // ==============================
    fun start_To_Set_1() {
        Animator.transition(motion, start_to_set_1, 1000)
    }

    fun start_To_Set_3() {
        Animator.transition(motion, start_to_set_3, 300)
    }

    fun start_To_Set_6() {
        Animator.transition(motion, start_to_set_6, 1000)
    }

    fun start_To_Set_7() {
        Animator.transition(motion, start_to_set_7, 1000)
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

    fun set_2_To_Set_16() {
        Animator.transition(motion, set_2_to_set_16, 1000)
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

    fun set_3_To_Set_8() {
        Animator.transition(motion, set_3_to_set_8, 1000)
    }

    fun set_5_To_Set_3() {
        Animator.transition(motion, set_5_to_set_3, 1000)
    }

    fun set_7_To_Start() {
        Animator.transition(motion, set_7_to_start, 1000)
    }

    fun set_7_To_Set_9() {
        Animator.transition(motion, set_7_to_set_9, 1000)
    }

    fun set_8_To_Set_3() {
        Animator.transition(motion, set_8_to_set_3, 1000)
    }

    fun set_8_To_Set_9() {
        Animator.transition(motion, set_8_to_set_9, 1000)
    }

    fun set_9_To_Set_7() {
        Animator.transition(motion, set_9_to_set_7, 0)
    }

    fun set_9_To_Set_8() {
        Animator.transition(motion, set_9_to_set_8, 0)
    }

    fun set_9_To_Set_10() {
        Animator.transition(motion, set_9_to_set_10, 1000)
    }

    fun set_9_To_Set_11() {
        Animator.transition(motion, set_9_to_set_11, 1000)
    }

    fun set_10_To_Set_11() {
        Animator.transition(motion, set_10_to_set_11, 300)
    }

    fun set_10_To_Set_14() {
        Animator.transition(motion, set_10_to_set_14, 1000)
    }

    fun set_11_To_Set_10() {
        Animator.transition(motion, set_11_to_set_10, 300)
    }

    fun set_11_To_Set_12() {
        Animator.transition(motion, set_11_to_set_12, 300)
    }

    fun set_11_To_Set_13() {
        Animator.transition(motion, set_11_to_set_13, 1000)
    }

    fun set_11_To_Set_14() {
        Animator.transition(motion, set_11_to_set_14, 1000)
    }

    fun set_13_To_Set_11() {
        Animator.transition(motion, set_13_to_set_11, 1000)
    }

    fun set_14_To_Set_10() {
        Animator.transition(motion, set_14_to_set_10, 1000)
    }

    fun set_14_To_Set_11() {
        Animator.transition(motion, set_14_to_set_11, 1000)
    }

    fun set_14_To_Set_15() {
        Animator.transition(motion, set_14_to_set_15, 1000)
    }

    fun set_15_To_Set_14() {
        Animator.transition(motion, set_15_to_set_14, 1000)
    }

    fun set_15_To_Set_17() {
        Animator.transition(motion, set_15_to_set_17, 1000)
    }

    fun set_16_To_Set_2() {
        Animator.transition(motion, set_16_to_set_2, 1000)
    }

    fun set_17_To_Set_15() {
        Animator.transition(motion, set_17_to_set_15, 1000)
    }

}