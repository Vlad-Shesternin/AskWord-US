package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.autofill.AutofillId
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.text.isDigitsOnly
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemAdditionalTranslationBinding
import com.veldan.askword_us.databinding.LayoutWordCreationStartBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.layout_additional_translation.view.*
import kotlinx.android.synthetic.main.layout_prompt.view.*
import kotlinx.android.synthetic.main.layout_word_creation_set_1.view.*

@RequiresApi(Build.VERSION_CODES.O)
class WordCreationAnimator(
    private val layoutWordCreation: LayoutWordCreationStartBinding,
    val context: Context
) :
    View.OnClickListener,
    TransitionListener {

    val TAG = "WordCreationAnimator"

    // Components
    private val inflater = LayoutInflater.from(context)

    // Components UI
    private var motion: MotionLayout
    private var ifvPromptAdd: ImageFilterView
    private var tvTranslation: TextView
    private var editTranslation: EditText
    private var ibAdditionalTranslation: ImageButton
    private var ifvListAdditionalTranslation: ImageFilterView
    private var layoutListAdditionalTranslation: ConstraintLayout

    // Layouts
    private val start = R.layout.layout_word_creation_start
    private val set_2 = R.layout.layout_word_creation_set_2
    private val set_3 = R.layout.layout_word_creation_set_3
    private val set_4 = R.layout.layout_word_creation_set_4

    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1
    private val start_to_set_3 = R.id.start_to_set_3
    private val start_to_set_4 = R.id.start_to_set_4

    // init Components
    init {
        layoutWordCreation.also {
            motion = it.motionWordCreation
            ifvPromptAdd = it.ifvPromptAdd
            tvTranslation = it.tvTranslation
            editTranslation = it.editTranslation
            ibAdditionalTranslation = it.ibAdditionalTranslation
            ifvListAdditionalTranslation = it.ifvListAdditionalTranslation
            layoutListAdditionalTranslation = it.layoutAdditionalTranslation.layoutAdditionalTranslation
        }
    }

    // init Events
    init {
        motion.setTransitionListener(this)
        ifvPromptAdd.setOnClickListener(this)
        ibAdditionalTranslation.setOnClickListener(this)
        ifvListAdditionalTranslation.setOnClickListener(this)
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
            ifvListAdditionalTranslation.id to start -> start_To_Set_4()
            ifvListAdditionalTranslation.id to set_4 -> set_4_To_Start()
        }
    }


    private val scrollIds = mutableListOf<Int>()
    private var increment = 0

    // ==============================
    // FillLayoutListAdditionalTranslation
    // ==============================
    private fun fillLayoutListAdditionalTranslation(translation: String) {
        ItemAdditionalTranslationBinding.inflate(inflater).apply {
            itemAdditionalTranslation.text = translation
            val scroll = scrollHItemAdditionalTranslation
            val delete = DeleteBinding.inflate(inflater).ivDelete

            scroll.id = ("1$increment").toInt()
            delete.id = ("2$increment").toInt()

            val set = ConstraintSet()

            layoutListAdditionalTranslation.also { layout ->
                layout.addView(scroll)
                layout.addView(delete)

                set.clone(layout)

                // connect scroll
                set.constrainWidth(scroll.id, 0)
                set.connect(scroll.id, START, PARENT_ID, START, 10)
                set.connect(scroll.id, END, layout.guideV_80.id, START, 10)
                // connect delete
                set.constrainHeight(delete.id, 0)
                set.constrainWidth(delete.id, 0)
                set.setDimensionRatio(delete.id, "1:1")
                set.connect(delete.id, START, layout.guideV_80.id, END, 10)
                set.connect(delete.id, END, PARENT_ID, END, 10)

                if (scrollIds.isEmpty()) {
                    // connect scroll
                    set.connect(scroll.id, TOP, PARENT_ID, TOP, 10)
                    // connect delete
                    set.connect(delete.id, TOP, scroll.id, TOP)
                    set.connect(delete.id, BOTTOM, scroll.id, BOTTOM)
                } else {
                    // connect scroll
                    set.connect(scroll.id, TOP, scrollIds.last(), BOTTOM)
                    // connect delete
                    set.connect(delete.id, TOP, scroll.id, TOP)
                    set.connect(delete.id, BOTTOM, scroll.id, BOTTOM)
                }
                // add ids to list
                scrollIds.add(scroll.id)
                increment++

                set.applyTo(layout)
            }
            delete.setOnClickListener {
                layoutListAdditionalTranslation.also { layout ->
                    set.clone(layout)
                    set.constrainHeight(scroll.id, 0)
                    scrollIds.remove(scroll.id)
                    set.applyTo(layout)
                }
            }
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
    private fun start_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, start_to_set_1, 1000)
        }
    }

    private fun start_To_Set_3() {
        editTranslation.also { edit ->
            fillLayoutListAdditionalTranslation(edit.text.toString())
            tvTranslation.text = edit.text
            edit.hint = ""
            edit.text.clear()
        }
        Animator.apply {
            transitionToEnd(motion, start_to_set_3, 400)
        }
    }

    private fun start_To_Set_4() {
        Animator.apply {
            transitionToEnd(motion, start_to_set_4, 1000)
        }
    }

    // ==============================
    //        transitionToStart
    // ==============================
    private fun set_4_To_Start() {
        Animator.apply {
            transitionToStart(motion, start_to_set_4, 1000)
        }
    }
}