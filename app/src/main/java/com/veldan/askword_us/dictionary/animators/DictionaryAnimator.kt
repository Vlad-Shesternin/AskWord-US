package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryStartBinding
import com.veldan.askword_us.databinding.LayoutWordCreationStartBinding
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.fragment_dictionary_set_6.view.*
import kotlinx.android.synthetic.main.layout_word_creation_set_1.view.*

class DictionaryAnimator(
    private val layoutDictionary: FragmentDictionaryStartBinding,
    val context: Context,
) :
    View.OnClickListener,
    View.OnLongClickListener,
    TransitionListener {

    // Components
    private val motion = layoutDictionary.motionDictionary
    private val fabAdd = layoutDictionary.fabAdd
    private val fabCategory = layoutDictionary.fabCategory
    private val fabPhoto = layoutDictionary.fabPhoto
    private val fabFile = layoutDictionary.fabFile

    // TransitionIds
    private val start_To_set_1 = R.id.start_to_set_1
    private val set_1_To_set_2 = R.id.set_1_to_set_2
    private val set_2_To_set_3 = R.id.set_2_to_set_3
    private val set_2_To_set_4 = R.id.set_2_to_set_4
    private val set_2_To_set_5 = R.id.set_2_to_set_5
    private val start_To_set_6 = R.id.start_to_set_6

    // States
    private val start = R.layout.fragment_dictionary_start
    private val set_1 = R.layout.fragment_dictionary_set_1
    private val set_2 = R.layout.fragment_dictionary_set_2

    // WordCreation(layout_word_creation)
    init {
        WordCreationAnimator(
            layoutDictionary.layoutWordCreationStart,
            context
        )
    }

    // Events (fragment_dictionary)
    init {
        // OnClick
        fabAdd.setOnClickListener(this)
        fabFile.setOnClickListener(this)
        fabPhoto.setOnClickListener(this)
        fabCategory.setOnClickListener(this)
        // OnLongClick
        fabAdd.setOnLongClickListener(this)
        // OnTransition
        motion.setTransitionListener(this)
    }

    // ==============================
    //          OnClick
    // ==============================
    override fun onClick(v: View?) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            fabAdd.id to start -> start_To_Set_1()
            fabAdd.id to set_2 -> set_2_To_Set_1()

            fabCategory.id to set_2 -> set_2_To_Set_3()
            fabPhoto.id to set_2 -> set_2_To_Set_4()
            fabFile.id to set_2 -> set_2_To_Set_5()
        }
    }

    // ==============================
    //          OnLongClick
    // ==============================
    override fun onLongClick(v: View?): Boolean {
        when (v?.id to motion.currentState) {
            fabAdd.id to start -> start_To_Set_6()
        }
        return true
    }

    // ==============================
    //          OnTransition
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {
        super.onTransitionCompleted(motionLayout, end)
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