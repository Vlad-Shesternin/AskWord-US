package com.veldan.askword_us.dictionary

import android.view.View
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.lifecycle.ViewModel
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.dictionary.word_creator.WordCreatorDialog
import com.veldan.askword_us.global.objects.Animator

class DictionaryAnimatorViewModel(
    private val layoutDictionary: FragmentDictionaryBinding,
    private val fragment: DictionaryFragment
) : ViewModel() {
    val TAG = "ccc"

    // Components UI
    private var motion: MotionLayout
    private var fabAdd: ImageFilterView
    private var fabFile: ImageButton
    private var fabPhoto: ImageButton
    private var fabCategory: ImageButton

    // TransitionIds
    private val start_To_set_1 = R.id.start_to_set_1
    private val set_1_To_set_2 = R.id.set_1_to_set_2
    private val set_2_To_set_3 = R.id.set_2_to_set_3
    private val set_2_To_set_4 = R.id.set_2_to_set_4
    private val set_2_To_set_5 = R.id.set_2_to_set_5
    private val start_To_set_6 = R.id.start_to_set_6

    // States
    private val start = R.id.start
    private val set_1 = R.id.set_1
    private val set_2 = R.id.set_2

    // init ComponentsUI
    init {
        layoutDictionary.also {
            motion = it.motionDictionary
            fabAdd = it.fabAdd
            fabFile = it.fabFile
            fabPhoto = it.fabPhoto
            fabCategory = it.fabCategory
        }
    }

    // init WordCreator
    private fun initWordCreator() {
        WordCreatorDialog(layoutDictionary.layoutWordCreator, fragment)
    }

    // ==============================
    //    onClick
    // ==============================
    fun onClick(view: View) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (view.id to motion.currentState) {
            fabAdd.id to start -> {
                initWordCreator()
                start_To_Set_6()
            }

            fabAdd.id to set_2 -> set_2_To_Set_1()
            fabCategory.id to set_2 -> set_2_To_Set_3()
            fabPhoto.id to set_2 -> set_2_To_Set_4()
            fabFile.id to set_2 -> set_2_To_Set_5()
        }
    }

    // ==============================
    //    onLongClick
    // ==============================
    fun onLongClick(view: View) {
        when (view.id to motion.currentState) {
            fabAdd.id to start -> start_To_Set_1()
        }
    }

    fun onTransitionCompleted(end: Int) {
        when (Animator.previous to end) {
            start to set_1 -> set_1_To_Set_2()
            set_2 to set_1 -> set_1_To_Start()
        }
    }

    // ==============================
    //        transitionToEnd
    // ==============================
    private fun start_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, start_To_set_1, 1000)
            previous = start
        }
    }

    private fun set_1_To_Set_2() {
        Animator.apply {
            transitionToEnd(motion, set_1_To_set_2, 1000)
            previous = set_1
        }
    }

    private fun set_2_To_Set_3() {
        Animator.apply {
            transitionToEnd(motion, set_2_To_set_3, 1000)
            previous = set_2
        }
    }

    private fun set_2_To_Set_4() {
        Animator.apply {
            transitionToEnd(motion, set_2_To_set_4, 1000)
            previous = set_2
        }
    }

    private fun set_2_To_Set_5() {
        Animator.apply {
            transitionToEnd(motion, set_2_To_set_5, 1000)
            previous = set_2
        }
    }

    private fun start_To_Set_6() {
        Animator.apply {
            transitionToEnd(motion, start_To_set_6, 1000)
            previous = start
        }
    }

    // ==============================
    //        transitionToStart
    // ==============================
    private fun set_1_To_Start() {
        Animator.apply {
            transitionToStart(motion, start_To_set_1, 1000)
            previous = set_1
        }
    }

    private fun set_2_To_Set_1() {
        Animator.apply {
            transitionToStart(motion, set_1_To_set_2, 1000)
            previous = set_2
        }
    }
}