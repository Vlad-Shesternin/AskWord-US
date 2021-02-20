package com.veldan.askword_us.studying

import android.app.ActionBar
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseDatabaseDao
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseModel
import com.veldan.askword_us.database.selected_word.SelectedWordDatabaseDao
import com.veldan.askword_us.database.selected_word.SelectedWordModel
import com.veldan.askword_us.databinding.*
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_ADDITIONAL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_FILL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_SELECTION
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_PHRASE
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_WORD
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.STUDY_FORMAT
import com.veldan.askword_us.global.interfaces.TextChangeListener
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.toast
import kotlinx.android.synthetic.main.fragment_studying.view.*
import kotlinx.android.synthetic.main.layout_studying_phrase.view.*
import kotlinx.android.synthetic.main.layout_studying_word.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.layout_studying_word.view.guideH_50 as guideH_501

class StudyingFragment :
    Fragment(),
    View.OnClickListener,
    TransitionListener {
    private val TAG = this::class.simpleName

    // Binding
    private lateinit var binding: FragmentStudyingBinding

    // ComponentsUI
    private lateinit var fabSettings: ImageButton
    private lateinit var fabNext: ImageButton
    private lateinit var tvBackToStudyFormat: TextView
    private lateinit var tvBackToStudy: TextView

    // Components
    private lateinit var databaseDao: DatabaseDao
    private lateinit var selectedWordDatabaseDao: SelectedWordDatabaseDao
    private lateinit var selectedPhraseDatabaseDao: SelectedPhraseDatabaseDao
    private val animations = StudyingAnimations
    private val animator = Animator2
    private lateinit var listWords: MutableList<SelectedWordModel>
    private var listPhrases = mutableListOf<SelectedPhraseModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initComponentsUI()
        initListeners()
        initComponents()

        return binding.root
    }

    // ==============================
    //    init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentStudyingBinding.inflate(inflater)
    }

    // ==============================
    //    init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            fabSettings = it.fabSettings
            fabNext = it.fabNext
            tvBackToStudyFormat = it.tvUpdateFormat
            tvBackToStudy = it.tvUpdateWp
        }
    }

    // ==============================
    //    init Listeners
    // ==============================
    private fun initListeners() {
        // onClick
        fabSettings.setOnClickListener(this)
        fabNext.setOnClickListener(this)
        tvBackToStudy.setOnClickListener(this)
        tvBackToStudyFormat.setOnClickListener(this)
        // onTransition
        binding.motionStudyingFormats.setTransitionListener(this)
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initAnimator()
        initDao()
        initUI()
    }

    // ==============================
    //    init Animator
    // ==============================
    private fun initAnimator() {
        animator.motion = binding.root
    }

    // ==============================
    //    init Dao
    // ==============================
    private fun initDao() {
        val application = requireNotNull(activity).application
        MyDatabase.getInstance(application).also {
            databaseDao = it.databaseDao
            selectedWordDatabaseDao = it.selectedWordDatabaseDao
            selectedPhraseDatabaseDao = it.selectedPhraseDatabaseDao
        }
    }

    // ==============================
    //    init UI
    // ==============================
    private fun initUI() {
        val pref = SharedPreferences(this).initSharedPref(STUDY_FORMAT)
        animator.apply {
            motion = binding.motionStudyingFormats
            animations.apply {
                when {
                    pref.getBoolean(QUESTION_FORMAT_WORD, false) &&
                            pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                        "Word - Phrase".toast(requireContext())
                        //answerFormat(pref, )

                    }
                    pref.getBoolean(QUESTION_FORMAT_WORD, false) -> {
                        "Word".toast(requireContext())
                        selectedWordDatabaseDao.getAllSelectedWords()
                            ?.observe(viewLifecycleOwner, Observer {
                                listWords = it
                                generateQuestionWord(binding.layoutStudyingWord.root)
                                generateQuestionWord(binding.layoutStudyingWordNext.root)
                                answerFormat(it[0])
                            })
                        jumpToState(show_word)
                    }
                    pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                        "Phrase".toast(requireContext())
                        selectedPhraseDatabaseDao.getAllSelectedPhrase()
                            ?.observe(viewLifecycleOwner, Observer {
                                listPhrases = it
                                generateQuestionPhrase(binding.layoutStudyingPhrase.root)
                                generateQuestionPhrase(binding.layoutStudyingPhraseNext.root)
                            })
                        jumpToState(show_phrase)
                    }
                }
            }
        }
    }

    // ==============================
    //    Answer Format
    // ==============================
    private fun answerFormat(
        selectedWordModel: SelectedWordModel,
    ) {
        val answersFormats = listOf(
            ANSWER_FORMAT_FILL,
            ANSWER_FORMAT_SELECTION,
            ANSWER_FORMAT_ADDITIONAL
        )
        val pref = SharedPreferences(this).initSharedPref(STUDY_FORMAT)
        val answersFormatsMap = answersFormats.associateWith { pref.getBoolean(it, false) }
        val answerFormats_value = answersFormatsMap.toList()

        for (pair in answerFormats_value) {
            when (pair) {
                ANSWER_FORMAT_FILL to true -> {
                    "FILL".toast(requireContext())
                    generateAnswerFormatFill(binding.layoutStudyingWord.root, selectedWordModel)
                    generateAnswerFormatFill(binding.layoutStudyingWordNext.root, selectedWordModel)
                }
                ANSWER_FORMAT_SELECTION to true -> {
                    "SELECTION".toast(requireContext())
                }
                ANSWER_FORMAT_ADDITIONAL to true -> {
                    "ADDITIONAL".toast(requireContext())
                }
            }
        }
    }

    // ==============================
    //    Generate Format Fill
    // ==============================
    private val previousIds = mutableListOf<Int>()
    private var previousViewId = 0
    private var increment = 0
        get() {
            ++field
            previousIds.add(field)
            previousViewId = field - 1
            return field
        }

    private fun generateAnswerFormatFill(
        constraintLayout: ConstraintLayout,
        selectedWordModel: SelectedWordModel,
    ) {
        increment = 0
        previousIds.clear()
        val translations = selectedWordModel.translations.shuffled()

        val scrollHAnswer: HorizontalScrollView
        val constraintAnswer: ConstraintLayout

        if (translations[0].count() >= 7) {
            ContainerAnswerStartBinding.inflate(layoutInflater).also {
                scrollHAnswer = it.scrollHAnswer
                constraintAnswer = it.constraintAnswer
            }
        } else {
            ContainerAnswerCenterBinding.inflate(layoutInflater).also {
                scrollHAnswer = it.scrollHAnswer
                constraintAnswer = it.constraintAnswer
            }
        }

        // VALUES
        val SCROLL_H_ANSWER = scrollHAnswer.id
        val GUIDE_H_50_ID = constraintLayout.guideH_50.id

        for ((index, value) in translations[0].withIndex()) {
            val editAnswer = ItemAnswerBinding.inflate(layoutInflater).root
            val ITEM_ANSWER_ID = editAnswer.apply { id = increment }.id
            editAnswer.setTextColor(resources.getColor(R.color.colorPrimary))
            editAnswer.background = resources.getDrawable(R.drawable.shape_answer_rect)
            constraintAnswer.addView(editAnswer)
            ConstraintSet().apply {
                clone(constraintAnswer)
                constrainWidth(ITEM_ANSWER_ID, 0)
                constrainPercentWidth(ITEM_ANSWER_ID, 10F)
                setDimensionRatio(ITEM_ANSWER_ID, "1:1")
                if (index == translations[0].lastIndex) {
                    val views = previousIds.toIntArray()

                    createHorizontalChain(
                        PARENT_ID, LEFT,
                        PARENT_ID, RIGHT,
                        views, null,
                        CHAIN_SPREAD)
                }
            }.applyTo(constraintAnswer)
            editAnswer.addTextChangedListener(
                object : TextChangeListener {
                    override fun afterTextChanged(s: Editable?) {
                        super.afterTextChanged(s)

                        editAnswer.setTextColor(resources.getColor(R.color.colorRed))
                        editAnswer.background =
                            resources.getDrawable(R.drawable.shape_answer_rect_red)

                        val charAnswer = translations[0][ITEM_ANSWER_ID - 1]
                        if (s.toString().equals(charAnswer.toString(), true)) {
                            editAnswer.setTextColor(resources.getColor(R.color.colorGreen))
                            editAnswer.background =
                                resources.getDrawable(R.drawable.shape_answer_rect_green)
                            if (index != translations[0].lastIndex) {
                                Log.i(TAG, "afterTextChanged: ddd")
                                editAnswer.nextFocusForwardId = editAnswer.id+1
                            }
                        }
                    }
                }
            )
        }

        constraintLayout.addView(scrollHAnswer)
        ConstraintSet().apply {
            clone(constraintLayout)
            constrainWidth(SCROLL_H_ANSWER, 0)
            connect(SCROLL_H_ANSWER, TOP, GUIDE_H_50_ID, BOTTOM)
            connect(SCROLL_H_ANSWER, BOTTOM, PARENT_ID, BOTTOM)
            connect(SCROLL_H_ANSWER, START, PARENT_ID, START, 50)
            connect(SCROLL_H_ANSWER, END, PARENT_ID, END, 50)
        }.applyTo(constraintLayout)
    }

    // ==============================
    //    Generate Question Word
    // ==============================
    private fun generateQuestionWord(layoutWord: ConstraintLayout) {
        val words = listWords.shuffled()
        CoroutineScope(Dispatchers.Main).launch {
            layoutWord.apply {
                delay(100)
                Glide.with(this)
                    .load(words[0].image)
                    .into(iv_image)
                tv_word.text = words[0].word
            }
        }
    }

    // ==============================
    //    Generate Question Phrase
    // ==============================
    private fun generateQuestionPhrase(layoutPhrase: ConstraintLayout) {
        layoutPhrase.apply {
            val phrases = listPhrases.shuffled()
            scroll_phrase.tv_question_phrase.text = phrases[0].phrase
            edit_answer_phrase.text.clear()
            listeningEditTextAnswerPhrase(edit_answer_phrase, phrases[0].translation)
        }
    }

    // ==============================
    //    Listening answer phrase
    // ==============================
    private fun listeningEditTextAnswerPhrase(
        editAnswerPhrase: EditText,
        answerPhrase: String,
    ) {
        editAnswerPhrase.addTextChangedListener(
            object : TextChangeListener {
                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)

                    editAnswerPhrase.setTextColor(resources.getColor(R.color.colorRed))
                    if (s.toString() == answerPhrase) {
                        Log.i(TAG, "()()()$s")
                        editAnswerPhrase.setTextColor(resources.getColor(R.color.colorAccent))
                    }
                }
            })
    }

    // ==============================
    //    transitionToStudyFormat
    // ==============================
    private fun transitionToStudyFormat() {
        // Надо бы отдельно удаление написать. Но как-то спешу, но в качественных проектах, создавай методы определённого назначения.
        SharedPreferences(this).initSharedPref(STUDY_FORMAT).edit().clear().apply()
        findNavController().popBackStack()
    }

    // ==============================
    //    transitionToStudy
    // ==============================
    private fun transitionToStudy() {
        SharedPreferences(this).initSharedPref(STUDY_FORMAT).edit().clear().apply()
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.selectedWordsDelete()
            databaseDao.selectedPhrasesDelete()
        }
        findNavController().popBackStack(R.id.studyFragment, false)
    }


    // ==============================
    //    on Click
    // ==============================
    override fun onClick(v: View) {
        animator.apply {
            animations.apply {
                when (v.id) {
                    fabSettings.id -> {
                        motion = binding.root
                        when (motion.currentState) {
                            start -> {
                                transition(motion.currentState to show_settings)
                            }
                            show_settings -> {
                                transition(show_settings to start)
                            }
                        }
                    }
                    fabNext.id -> {
                        motion = binding.motionStudyingFormats
                        val pref =
                            SharedPreferences(this@StudyingFragment).initSharedPref(STUDY_FORMAT)
                        when {
                            pref.getBoolean(QUESTION_FORMAT_WORD, false) &&
                                    pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {

                            }
                            pref.getBoolean(QUESTION_FORMAT_WORD, false) -> {
                                when (motion.currentState) {
                                    show_word -> transition(show_word to word_next)
                                    jump_to_next_word -> transition(jump_to_next_word to word_next_2)
                                    jump_to_next_word_2 -> transition(jump_to_next_word_2 to word_next)
                                }
                            }
                            pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                                when (motion.currentState) {
                                    show_phrase -> transition(show_phrase to phrase_next)
                                    jump_to_next_phrase -> transition(jump_to_next_phrase to phrase_next_2)
                                    jump_to_next_phrase_2 -> transition(jump_to_next_phrase_2 to phrase_next)
                                }
                            }
                        }
                    }
                    tvBackToStudy.id -> {
                        transitionToStudy()

                    }
                    tvBackToStudyFormat.id -> {
                        val pref =
                            SharedPreferences(this@StudyingFragment).initSharedPref(
                                STUDY_FORMAT)
                        if (pref.getBoolean(QUESTION_FORMAT_PHRASE, false)) {
                            CoroutineScope(Dispatchers.IO).launch {
                                databaseDao.selectedPhrasesDelete()
                            }
                        }
                        transitionToStudyFormat()
                    }
                }
            }
        }
    }

    // ==============================
    //    on Transition Completed
    // ==============================
    override fun onTransitionCompleted(motion: MotionLayout?, stateEnd: Int) {
        animator.apply {
            animations.apply {
                when (stateEnd) {
                    word_next -> {
                        generateQuestionWord(binding.layoutStudyingWord.root)
                        jumpToState(jump_to_next_word)
                    }
                    word_next_2 -> {
                        generateQuestionWord(binding.layoutStudyingWordNext.root)
                        jumpToState(jump_to_next_word_2)
                    }
                    phrase_next -> {
                        generateQuestionPhrase(binding.layoutStudyingPhrase.root)
                        jumpToState(jump_to_next_phrase)
                    }
                    phrase_next_2 -> {
                        generateQuestionPhrase(binding.layoutStudyingPhraseNext.root)
                        jumpToState(jump_to_next_phrase_2)
                    }
                }
            }
        }
    }
}