package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.*
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.layout_prompt.view.*
import kotlinx.android.synthetic.main.layout_word_creation_set_1.view.*
import kotlin.math.log

class WordCreationAnimator(
    private val layoutWordCreation: LayoutWordCreationStartBinding,
    val context: Context,
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components layout_words_creation
    private val motion = layoutWordCreation.motionWordCreation
    private val ifvPromptAdd = layoutWordCreation.ifvPromptAdd
    private val ivImageAdd = layoutWordCreation.ivImgAdd

    // Layouts
    private val start = R.layout.layout_word_creation_start

    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1

    // Events (layout_words_creation)
    init {
        motion.setTransitionListener(this)
        ivImageAdd.setOnClickListener(this)
        ifvPromptAdd.setOnClickListener(this)
    }

    // ==============================
    //       OnTransitionChange
    // ==============================
    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        start: Int,
        end: Int,
        progress: Float
    ) {
        super.onTransitionChange(motionLayout, start, end, progress)

        if (R.layout.layout_word_creation_set_2 == end && 1.0f == progress) {
            Log.i(TAG, "onTransitionChange: 000000000000000000000000")
            motion.layout_prompt.edit_prompt.defaultFocusAndKeyboard(true)
        }
    }

        // ==============================
        //          OnClick
        // ==============================
        override fun onClick(v: View?) {
            // when use Pair<Int, Int>(v?.id, motion.currentState)
            when (v?.id to motion.currentState) {
                ifvPromptAdd.id to R.layout.layout_word_creation_start -> start_To_Set_1()
            }
        }

        // ==============================
        //      transitionToEnd
        // ==============================
        private fun start_To_Set_1() {
            Log.i(TAG, "start_To_Set_1:")
            Animator.apply {
                transitionToEnd(motion, start_to_set_1, 1000)
            }
        }

        // ==============================
        //      StartToMoveTranslation
        // ==============================
        val additionalTranslationList = arrayListOf<String>()
//    private fun startToMoveTranslation() {
//
//        if (editTranslation.text.toString().isNotEmpty()) {
//            additionalTranslationList.add(editTranslation.text.toString())
//
////            CoroutineScope(Dispatchers.Main).launch {
////                tvTranslation.textWithoutUnderline(editTranslation.text.toString())
////
////                ItemAdditionalTranslationBinding.inflate(LayoutInflater.from(context))
////                    .also {
////                        it.itemAdditionalTranslation.text = tvTranslation.text
////
////                        ConstraintSet().connect()
//////                        layoutAdditionalTranslation.addView(it.scrollHItemAdditionalTranslation)
////                    }
////                DeleteBinding.inflate(LayoutInflater.from(context))
////                    .also {
//////                        layoutDeleteAdditionalTranslation.addView(it.ivDelete)
////                    }
//
//                Animator.transition(motion, moveTranslation, 500)
//                delay(500)
//                tvTranslation.text = ""
//                editTranslation.text.clear()
//                motion.transitionToState(start)
//                editTranslation.defaultFocusAndKeyboard(true)
//            }
//        } else {
//            Log.i(TAG, "Введыть текст")
//        }
//        Log.i(TAG, "$additionalTranslationList")
//    }
    }