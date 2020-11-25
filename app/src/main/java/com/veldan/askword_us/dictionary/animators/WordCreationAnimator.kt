package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.LayoutWordCreationStartBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.layout_prompt.view.*
import kotlinx.android.synthetic.main.layout_word_creation_set_1.view.*

class WordCreationAnimator(
    private val layoutWordCreation: LayoutWordCreationStartBinding,
    val context: Context
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components
    private var motion: MotionLayout
    private var ifvPromptAdd: ImageFilterView
    private var tvTranslation: TextView
    private var editTranslation: EditText
    private var ibAdditionalTranslation: ImageButton
    private var ifvListAdditionalTranslation: ImageFilterView

    // Layouts
    private val start = R.layout.layout_word_creation_start
    private val set_2 = R.layout.layout_word_creation_set_2
    private val set_3 = R.layout.layout_word_creation_set_3

    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1
    private val start_to_set_3 = R.id.start_to_set_3

    // init Components
    init {
        layoutWordCreation.also {
            motion = it.motionWordCreation
            ifvPromptAdd = it.ifvPromptAdd
            tvTranslation = it.tvTranslation
            editTranslation = it.editTranslation
            ibAdditionalTranslation = it.ibAdditionalTranslation
            ifvListAdditionalTranslation = it.ifvListAdditionalTranslation
        }
    }

    // init Events
    init {
        motion.setTransitionListener(this)
        ifvPromptAdd.setOnClickListener(this)
        ibAdditionalTranslation.setOnClickListener(this)
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

        when (end to progress) {
            set_2 to 1.0f ->
                afterEndSet_2()
            set_3 to 1.0f -> {
                afterEndSet_3()
            }
        }
    }

    // ==============================
    //          OnClick
    // ==============================
    override fun onClick(v: View?) {
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            ifvPromptAdd.id to start -> start_To_Set_1()
            ibAdditionalTranslation.id to start -> start_To_Set_3()
        }
    }

    // ==============================
    //         afterEndSets
    // ==============================
    private fun afterEndSet_2() {
        motion.layout_prompt.edit_prompt.defaultFocusAndKeyboard(true)
    }

    private fun afterEndSet_3() {
        tvTranslation.text = ""
        editTranslation.hint = context.getString(R.string.word_creation_edit_enter_translation)
        motion.progress = 0F
    }

    // ==============================
    //        transitionToEnd
    // ==============================
    private fun start_To_Set_1()  {
        Animator.apply {
            transitionToEnd(motion, start_to_set_1, 1000)
        }
    }

    private val listAdditionalTranslation = mutableListOf<String>()
    private fun start_To_Set_3() {
        editTranslation.also { edit ->
            listAdditionalTranslation.add(edit.text.toString())
            tvTranslation.text = edit.text
            edit.hint = ""
            edit.text.clear()
        }
        Animator.apply {
            transitionToEnd(motion, start_to_set_3, 400)
        }
    }
}