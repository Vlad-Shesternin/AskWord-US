package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemAdditionalTranslationBinding
import com.veldan.askword_us.databinding.LayoutWordCreationBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import com.veldan.askword_us.global.textWithoutUnderline
import kotlinx.coroutines.*

class WordCreationAnimator(
    private val layoutWordsCreation: LayoutWordCreationBinding,
    val context: Context,
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components layout_words_creation
    private val motion = layoutWordsCreation.layoutWordsCreation
    private val editWord = layoutWordsCreation.editWord
    private val ivImgAdd = layoutWordsCreation.ivImgAdd
    private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd
    private val editTranslation = layoutWordsCreation.editTranslation
    private val tvTranslation = layoutWordsCreation.tvTranslation
    private val btnAdditionalTranslation = layoutWordsCreation.btnAdditionalTranslation
    private val ivListAdditionalTranslation = layoutWordsCreation.ivListAdditionalTranslation
    private val containerAdditionalTranslation =
        layoutWordsCreation.layoutAdditionalTranslation.containerAdditionalTranslation


    // ConstraintIds for dictionary_scene
    private val start = R.id.start
    private val moveToTop = R.id.move_to_top
    private val twistingShine = R.id.twisting_shine
    private val moveTranslation = R.id.move_translation
    private val appearanceLayoutAdditionalTranslation =
        R.id.appearance_layout_additional_translation

    // Events (layout_words_creation)
    init {
        motion.setTransitionListener(this)
        ifvPromptAdd.setOnClickListener(this)
        btnAdditionalTranslation.setOnClickListener(this)
        ivListAdditionalTranslation.setOnClickListener(this)
    }

    // ==============================
    //          OnClick
    // ==============================
    override fun onClick(v: View?) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            ifvPromptAdd.id to start -> startToMoveToTop()
          //  btnAdditionalTranslation.id to start -> startToMoveTranslation()
            ivListAdditionalTranslation.id to start -> startToAppearanceAdditionalTranslation()
            ivListAdditionalTranslation.id to appearanceLayoutAdditionalTranslation -> appearanceAdditionalTranslationToStart()
        }
    }

    // ==============================
    //      OnTransitionCompleted
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        super.onTransitionCompleted(motionLayout, currentId)
        if (currentId == twistingShine) {
            CoroutineScope(Dispatchers.Default).launch {
                delay(500)
                withContext(Dispatchers.Main) {
                    layoutWordsCreation.layoutPrompt.editPrompt.defaultFocusAndKeyboard(true)
                }
            }
        }
    }

    // ==============================
    //      StartToMoveToTop
    // ==============================
    private fun startToMoveToTop() {
        Animator.transitionToEnd(motion, moveToTop, 500)
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
        if (additionalTranslationList.isNotEmpty())
            Animator.transitionToEnd(motion, appearanceLayoutAdditionalTranslation, 500)
    }

    // ==============================
    // AppearanceAdditionalTranslationToStart
    // ==============================
    private fun appearanceAdditionalTranslationToStart() {
        Animator.transitionToEnd(motion, start, 500)
    }
}