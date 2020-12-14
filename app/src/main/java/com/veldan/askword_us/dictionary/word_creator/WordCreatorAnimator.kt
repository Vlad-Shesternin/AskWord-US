package com.veldan.askword_us.dictionary.word_creator

import android.annotation.SuppressLint
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.addTextChangedListener
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemTranslationBinding
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryFragment
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TextChangeListener
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator
import kotlinx.android.synthetic.main.layout_translations.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordCreatorAnimator(
    private val layoutWordCreator: LayoutWordCreatorBinding,
    private val fragment: DictionaryFragment
) : View.OnClickListener,
    TransitionListener,
    TextChangeListener {

    val TAG = "HOMOSAPIENC"

    // Components
    private val context = fragment.requireContext()
    private val inflater = LayoutInflater.from(context)
    private val scrollIds = mutableListOf<Int>()
    private var increment = 0

    // Layouts
    private val start = R.id.start
    private val set_2 = R.id.set_2
    private val set_3 = R.id.set_3
    private val set_4 = R.id.set_4
    private val set_5 = R.id.set_5

    // TransitionIds
    private val start_to_set_1 = R.id.start_to_set_1
    private val start_to_set_3 = R.id.start_to_set_3
    private val set_3_to_set_1 = R.id.set_3_to_set_1
    private val set_3_to_set_4 = R.id.set_3_to_set_4
    private val set_3_to_set_5 = R.id.set_3_to_set_5

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var ifvPromptAdd: ImageFilterView
    private lateinit var tvTranslation: TextView
    private lateinit var editTranslation: EditText
    private lateinit var layoutTranslations: ConstraintLayout
    private lateinit var ibTranslation: ImageButton
    private lateinit var ifvListTranslation: ImageFilterView

    // init
    init {
        initComponentUI()
        initListeners()
    }

    // ==============================
    //     Init Components UI
    // ==============================
    private fun initComponentUI() {
        layoutWordCreator.also {
            motion = it.motionWordCreator
            ifvPromptAdd = it.ifvPromptAdd
            tvTranslation = it.tvTranslation
            editTranslation = it.editTranslation
            layoutTranslations = it.layoutTranslations.layoutTranslations
            ibTranslation = it.ibTranslations
            ifvListTranslation = it.ifvListTranslations
        }
    }

    // ==============================
    //     Init Listeners
    // ==============================
    private fun initListeners() {
        // onClick
        ifvPromptAdd.setOnClickListener(this)
        ibTranslation.setOnClickListener(this)
        ifvListTranslation.setOnClickListener(this)
        // onTransition
        motion.setTransitionListener(this)
        // onChangeText
        editTranslation.addTextChangedListener(this)
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
                    set.connect(
                        delete.id,
                        ConstraintSet.BOTTOM,
                        scroll.id,
                        ConstraintSet.BOTTOM
                    )
                } else {
                    // connect scroll
                    set.connect(
                        scroll.id,
                        ConstraintSet.TOP, scrollIds.last(),
                        ConstraintSet.BOTTOM
                    )
                    // connect delete
                    set.connect(delete.id, ConstraintSet.TOP, scroll.id, ConstraintSet.TOP)
                    set.connect(
                        delete.id,
                        ConstraintSet.BOTTOM,
                        scroll.id,
                        ConstraintSet.BOTTOM
                    )
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
                    set_5_To_Set_3()
                }
            }
        }
    }

    // ==============================
    //    afterEndSets
    // ==============================
    private fun afterEndSet_2() {
        layoutWordCreator.editPrompt.defaultFocusAndKeyboard(true)
    }

    private fun afterEndSet_4() {
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
        Animator.apply {
            transitionToEnd(motion, start_to_set_3, 300)
        }
    }

    private fun set_3_To_Set_1() {
        Animator.apply {
            transitionToEnd(motion, set_3_to_set_1, 1000)
        }
    }

    private fun set_3_To_Set_4() {
        if (editTranslation.text.toString() != "") {
            editTranslation.also { edit ->
                WordCreatorDialog.translations.add(edit.text.toString())
                fillLayoutTranslations(edit.text.toString())
                tvTranslation.text = edit.text
                edit.hint = ""
                edit.text.clear()
            }
            Animator.apply {
                transitionToEnd(motion, set_3_to_set_4, 300)
            }
        }
    }

    private fun set_3_To_Set_5() {
        if (scrollIds.isNotEmpty()) {
            Animator.apply {
                transitionToEnd(motion, set_3_to_set_5, 1000)
            }
        }
    }

    // ==============================
    //    transitionToStart
    // ==============================
    private fun set_3_To_Start() {
        Animator.apply {
            transitionToStart(motion, start_to_set_3, 300)
        }
    }

    private fun set_5_To_Set_3() {
        Animator.apply {
            transitionToStart(motion, set_3_to_set_5, 1000)
        }
    }

    // ==============================
    //    Event Handlers
    // ==============================
    override fun onClick(view: View?) {
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (view!!.id to motion.currentState) {
            ifvPromptAdd.id to start -> start_To_Set_1()
            ifvPromptAdd.id to set_3 -> set_3_To_Set_1()

            ibTranslation.id to set_3 -> set_3_To_Set_4()

            ifvListTranslation.id to set_3 -> set_3_To_Set_5()
            ifvListTranslation.id to set_5 -> set_5_To_Set_3()
        }
    }

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
            set_4 to 1.0f -> {
                afterEndSet_4()
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)

        if (WordCreatorDialog.translations.isEmpty()) {
            when (s!!.length) {
                1 -> {
                    Log.i(TAG, "s to 3: ${WordCreatorDialog.translations}")
                    start_To_Set_3()
                }
                0 -> {
                    Log.i(TAG, "3 to s: ${WordCreatorDialog.translations}")
                    set_3_To_Start()
                }
            }
        }
    }
}