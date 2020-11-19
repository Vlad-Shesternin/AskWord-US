package com.veldan.askword_us.dictionary.animators

import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.LayoutWordCreationBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.general_classes.Components
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.coroutines.*

class WordCreationAnimator(
    private val layoutWordsCreation: LayoutWordCreationBinding,
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components layout_words_creation
    private val motion = layoutWordsCreation.layoutWordsCreation
    private val editWord = layoutWordsCreation.editWord
    private val imgImgAdd = layoutWordsCreation.imgImgAdd
    private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd
    private val editTranslation = layoutWordsCreation.editTranslation
    private val tvTranslation = layoutWordsCreation.tvTranslation
    private val btnAdditionalTranslation = layoutWordsCreation.btnAdditionalTranslation

    // ConstraintIds for dictionary_scene
    private val start = R.id.start
    private val moveToTop = R.id.move_to_top
    private val twistingShine = R.id.twisting_shine
    private val moveTranslation = R.id.move_translation

    // Events (layout_words_creation)
    init {
        motion.setTransitionListener(this)
        ifvPromptAdd.setOnClickListener(this)
        btnAdditionalTranslation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            ifvPromptAdd.id to start -> startToMoveToTop()
            btnAdditionalTranslation.id to start -> startToMoveTranslation()
        }
    }

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

    private fun startToMoveToTop() {
        val views = arrayOf(imgImgAdd, editWord, editTranslation)
        Components(*views).enabled(false)
        Animator.transition(motion, moveToTop, 500)
    }

    private val translationList = arrayListOf<String>()
    private fun startToMoveTranslation() {

        if (editTranslation.text.toString().isNotEmpty()) {
            translationList.add(editTranslation.text.toString())
            CoroutineScope(Dispatchers.Main).launch {
                tvTranslation.text = editTranslation.text
                Animator.transition(motion, moveTranslation, 500)
                delay(500)
                tvTranslation.text = ""
                editTranslation.text.clear()
                motion.transitionToState(start)
                editTranslation.defaultFocusAndKeyboard(true)
            }

        } else {
            Log.i(TAG, "Введыть текст")
        }
        Log.i(TAG, "$translationList")
    }
}