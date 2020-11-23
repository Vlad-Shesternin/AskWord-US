package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.*
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import com.veldan.askword_us.global.textWithoutUnderline
import kotlinx.coroutines.*

class WordCreationAnimator(
    private val layoutWordsCreation: LayoutWordCreationStartBinding,
    val context: Context,
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components layout_words_creation
    private val motion = layoutWordsCreation.motionWordsCreation
    private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd
    private val ivImageAdd = layoutWordsCreation.ivImgAdd


    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1

    // Events (layout_words_creation)
    init {
        ifvPromptAdd.setOnClickListener(this)
        ivImageAdd.setOnClickListener(this)
        //motion.setTransitionListener(this)

    }

    // ==============================
    //          OnClick
    // ==============================
    override fun onClick(v: View?) {
        Log.i(TAG, "onClick: sssssssssssssssssss")
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            ifvPromptAdd to R.layout.layout_word_creation_start -> start_To_Set_1()
        }
    }

    // ==============================
    //      OnTransitionCompleted
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        super.onTransitionCompleted(motionLayout, currentId)
//        if (currentId == twistingShine) {
//            CoroutineScope(Dispatchers.Default).launch {
//                delay(500)
//                withContext(Dispatchers.Main) {
//                    layoutWordsCreation.layoutPrompt.editPrompt.defaultFocusAndKeyboard(true)
//                }
//            }
//        }
    }

    // ==============================
    //      transitionToEnd
    // ==============================
    private fun start_To_Set_1() {
        Animator.transitionToEnd(motion, start_to_set_1, 1000)
    }

    // ==============================
    //      StartToMoveTranslation
    // ==============================
    private val additionalTranslationList = arrayListOf<String>()
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

    // ==============================
    // StartToAppearanceAdditionalTranslation
    // ==============================
    private fun startToAppearanceAdditionalTranslation() {
        if (additionalTranslationList.isNotEmpty()) {
//            Animator.transitionToEnd(motion, appearanceLayoutAdditionalTranslation, 500)

        }
    }

    // ==============================
    // AppearanceAdditionalTranslationToStart
    // ==============================
    private fun appearanceAdditionalTranslationToStart() {
        //  Animator.transitionToEnd(motion, start, 500)
    }
}