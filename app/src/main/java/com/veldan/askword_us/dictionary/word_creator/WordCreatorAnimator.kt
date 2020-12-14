package com.veldan.askword_us.dictionary.word_creator

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator

object WordCreatorAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val set_2 = R.id.set_2
    const val set_3 = R.id.set_3
    const val set_4 = R.id.set_4
    const val set_5 = R.id.set_5

    // TransitionIds
    private const val start_to_set_1 = R.id.start_to_set_1
    private const val start_to_set_3 = R.id.start_to_set_3
    private const val set_3_to_set_1 = R.id.set_3_to_set_1
    private const val set_3_to_set_4 = R.id.set_3_to_set_4
    private const val set_3_to_set_5 = R.id.set_3_to_set_5

    // ==============================
    //    transitionToEnd
    // ==============================
    fun start_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, start_to_set_1, 1000)
        }
    }

    fun start_To_Set_3() {
        Animator.apply {
            transitionToEnd(motion, start_to_set_3, 300)
        }
    }

    fun set_3_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, set_3_to_set_1, 1000)
        }
    }

    fun set_3_To_Set_4() {
        Animator.apply {
            transitionToEnd(motion, set_3_to_set_4, 300)
        }
    }

    fun set_3_To_Set_5() {
        Animator.apply {
            transitionToEnd(motion, set_3_to_set_5, 1000)
        }
    }

    // ==============================
    //    transitionToStart
    // ==============================
    fun set_3_To_Start() {
        Animator.apply {
            transitionToStart(motion, start_to_set_3, 300)
        }
    }

    fun set_5_To_Set_3() {
        Animator.apply {
            transitionToStart(motion, set_3_to_set_5, 1000)
        }
    }
}