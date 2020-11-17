package com.veldan.askword_us.dictionary

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.LayoutWordsCreationBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.general_classes.Components
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.coroutines.*

class PromptAnimation(
    private val layoutWordsCreation: LayoutWordsCreationBinding
):
    View.OnClickListener,
    TransitionListener {

    //Components layout_words_creation
    private val motion = layoutWordsCreation.layoutWordsCreation
    private val editWord = layoutWordsCreation.editWord
    private val imgImgAdd = layoutWordsCreation.imgImgAdd
    private val editTranslation = layoutWordsCreation.editTranslation
    private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd

    //ConstraintIds for dictionary_scene
    private val start = R.id.start
    private val moveToTop = R.id.move_to_top
    private val twistingShine = R.id.twisting_shine

    //Events (layout_words_creation)
    init {
        motion.setTransitionListener(this)
        ifvPromptAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            ifvPromptAdd.id to start -> startToMoveToTop()
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
        Animator.transition(motion, start, moveToTop, 500)
    }
}