package com.veldan.askword_us.dictionary.word_creator

import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModel
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemTranslationBinding
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryFragment
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.layout_prompt.view.*
import kotlinx.android.synthetic.main.layout_translations.view.*
import kotlinx.android.synthetic.main.layout_word_creator.view.*

class WordCreatorAnimatorViewModel(
    private val layoutWordCreator: LayoutWordCreatorBinding,
    fragment: DictionaryFragment
) : ViewModel() {

    // Components
    private val context = fragment.requireContext()
    private val inflater = LayoutInflater.from(context)
    private val translations = mutableListOf<String>()
    private val scrollIds = mutableListOf<Int>()
    private var increment = 0

    // Layouts
    private val start = R.id.start
    private val set_2 = R.id.set_2
    private val set_3 = R.id.set_3
    private val set_4 = R.id.set_4

    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1
    private val start_to_set_3 = R.id.start_to_set_3
    private val start_to_set_4 = R.id.start_to_set_4

    // Components UI
    private var motion: MotionLayout
    private var ifvPromptAdd: ImageFilterView
    private var tvTranslation: TextView
    private var editTranslation: EditText
    private var layoutTranslations: ConstraintLayout
    private var ibAdditionalTranslation: ImageButton
    private var ifvListAdditionalTranslation: ImageFilterView

    // init Components UI
    init {
        layoutWordCreator.also {
            motion = it.motionWordCreator
            ifvPromptAdd = it.ifvPromptAdd
            tvTranslation = it.tvTranslation
            editTranslation = it.editTranslation
            layoutTranslations = it.layoutTranslations.layoutTranslations
            ibAdditionalTranslation = it.ibTranslations
            ifvListAdditionalTranslation = it.ifvListTranslations
        }
    }

    // ==============================
    //     fillLayoutTranslations
    // ==============================
    private fun fillLayoutTranslations(translation: String) {
        ItemTranslationBinding.inflate(inflater).apply {
            itemTranslation.text = translation
            val scroll = scrollHItemTranslation
            val delete = DeleteBinding.inflate(inflater).ivDelete

            scroll.id = ("1$increment").toInt()
            delete.id = ("2$increment").toInt()

            val set = ConstraintSet()

            layoutTranslations.also { layout ->
                layout.visibility = View.VISIBLE
                layout.addView(scroll)
                layout.addView(delete)

                set.clone(layout)

                // connect scroll
                set.constrainWidth(scroll.id, 0)
                set.connect(
                    scroll.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START, 10
                )
                set.connect(
                    scroll.id,
                    ConstraintSet.END, layout.guideV_80.id,
                    ConstraintSet.START, 10
                )
                // connect delete
                set.constrainHeight(delete.id, 0)
                set.constrainWidth(delete.id, 0)
                set.setDimensionRatio(delete.id, "1:1")
                set.connect(
                    delete.id,
                    ConstraintSet.START, layout.guideV_80.id,
                    ConstraintSet.END, 10
                )
                set.connect(
                    delete.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END, 10
                )

                if (scrollIds.isEmpty()) {
                    // connect scroll
                    set.connect(
                        scroll.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP, 10
                    )
                    // connect delete
                    set.connect(delete.id, ConstraintSet.TOP, scroll.id, ConstraintSet.TOP)
                    set.connect(delete.id, ConstraintSet.BOTTOM, scroll.id, ConstraintSet.BOTTOM)
                } else {
                    // connect scroll
                    set.connect(
                        scroll.id,
                        ConstraintSet.TOP, scrollIds.last(),
                        ConstraintSet.BOTTOM
                    )
                    // connect delete
                    set.connect(delete.id, ConstraintSet.TOP, scroll.id, ConstraintSet.TOP)
                    set.connect(delete.id, ConstraintSet.BOTTOM, scroll.id, ConstraintSet.BOTTOM)
                }
                // add ids to list
                scrollIds.add(scroll.id)
                increment++

                set.applyTo(layout)
            }
            delete.setOnClickListener {
                layoutTranslations.also { layout ->
                    set.clone(layout)
                    set.constrainHeight(scroll.id, 0)
                    scrollIds.remove(scroll.id)
                    set.applyTo(layout)
                }
                if (scrollIds.isEmpty()) {
                    layoutTranslations.visibility = View.INVISIBLE
                    set_4_To_Start()
                }
            }
        }
    }

    // ==============================
    //    afterEndSets
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
    //    transitionToEnd
    // ==============================
    private fun start_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, start_to_set_1, 1000)
        }
    }

    private fun start_To_Set_3() {
        if (editTranslation.text.toString() != "") {
            editTranslation.also { edit ->
                translations.add(edit.text.toString())
                fillLayoutTranslations(edit.text.toString())
                tvTranslation.text = edit.text
                edit.hint = ""
                edit.text.clear()
            }
            Animator.apply {
                transitionToEnd(motion, start_to_set_3, 400)
            }
        }
    }

    private fun start_To_Set_4() {
        if (translations.isNotEmpty()) {
            Animator.apply {
                transitionToEnd(motion, start_to_set_4, 1000)
            }
        }
    }

    // ==============================
    //    transitionToStart
    // ==============================
    private fun set_4_To_Start() {
        Animator.apply {
            transitionToStart(motion, start_to_set_4, 1000)
        }
    }

    // ==============================
    //    onClick
    // ==============================
    fun onClick(view: View) {
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (view.id to motion.currentState) {
            ifvPromptAdd.id to start -> start_To_Set_1()
            ibAdditionalTranslation.id to start -> start_To_Set_3()
            ifvListAdditionalTranslation.id to start -> start_To_Set_4()
            ifvListAdditionalTranslation.id to set_4 -> set_4_To_Start()
        }
    }

    // ==============================
    //    OnTransitionChange
    // ==============================
    fun onTransitionChange(end: Int, progress: Float) {
        when (end to progress) {
            set_2 to 1.0f ->
                afterEndSet_2()
            set_3 to 1.0f -> {
                afterEndSet_3()
            }
        }
    }
}