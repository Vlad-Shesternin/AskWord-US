package com.veldan.askword_us.dictionary

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.dictionary.word_creator.WordCreatorDialog
import com.veldan.askword_us.global.emums.Direction
import com.veldan.askword_us.global.emums.Direction.toEND
import com.veldan.askword_us.global.emums.Direction.toSTART
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


    // ==============================
    //    transitionToEnd
    // ==============================
    fun start_To_Set_1(motion: MotionLayout) {
        Animator.apply {
            transition(motion, start_To_set_1, 1000, toEND)
            previous = start
        }
    }

    fun set_1_To_Set_2() {
        Animator.apply {
            transition(motion, set_1_To_set_2, 1000, toEND)
            previous = set_1
        }
    }

    fun set_2_To_Set_3() {
        Animator.apply {
            transition(motion, set_2_To_set_3, 1000, toEND)
            previous = set_2
        }
    }

    fun set_2_To_Set_4() {
        Animator.apply {
            transition(motion, set_2_To_set_4, 1000, toEND)
            previous = set_2
        }
    }

    fun set_2_To_Set_5() {
        Animator.apply {
            transition(motion, set_2_To_set_5, 1000, toEND)
            previous = set_2
        }
    }

    fun start_To_Set_6() {
        Animator.apply {
            transition(motion, start_To_set_6, 1000, toEND)
            previous = start
        }
    }

    // ==============================
    //    transitionToStart
    // ==============================
    fun set_1_To_Start() {
        Animator.apply {
            transition(motion, start_To_set_1, 1000, toSTART)
            previous = set_1
        }
    }

    fun set_2_To_Set_1() {
        Animator.apply {
            transition(motion, set_1_To_set_2, 1000, toSTART)
            previous = set_2
        }
    }
}