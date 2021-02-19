package com.veldan.askword_us.studying

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseDatabaseDao
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseModel
import com.veldan.askword_us.database.selected_word.SelectedWordDatabaseDao
import com.veldan.askword_us.database.selected_word.SelectedWordModel
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyingBinding
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
                        answerFormat(pref)

                    }
                    pref.getBoolean(QUESTION_FORMAT_WORD, false) -> {
                        "Word".toast(requireContext())
                        answerFormat(pref)
                        jumpToState(show_word)
                    }
                    pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                        "Phrase".toast(requireContext())
                        selectedPhraseDatabaseDao.getAllSelectedPhrase()
                            ?.observe(viewLifecycleOwner, Observer {
                                listPhrases = it
                                binding.layoutStudyingPhrase.tvQuestionPhrase.text =
                                    it[0].phrase
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
    private fun answerFormat(pref: android.content.SharedPreferences) {
        val answersFormats = listOf(
            ANSWER_FORMAT_FILL,
            ANSWER_FORMAT_SELECTION,
            ANSWER_FORMAT_ADDITIONAL
        )
        val answersFormatsMap = answersFormats.associateWith { pref.getBoolean(it, false) }
        val answerFormats_value = answersFormatsMap.toList()

        for (pair in answerFormats_value) {
            when (pair) {
                ANSWER_FORMAT_FILL to true -> {
                    "FILL".toast(requireContext())
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
    private fun listeningEditTextAnswerPhrase(editAnswerPhrase: EditText, answerPhrase: String) {
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
                        when (motion.currentState) {
                            show_phrase -> transition(show_phrase to phrase_next)
                            jump_to_next_phrase -> transition(jump_to_next_phrase to phrase_next_2)
                            jump_to_next_phrase_2 -> transition(jump_to_next_phrase_2 to phrase_next)
                        }
                    }
                    tvBackToStudy.id -> {
                        transitionToStudy()

                    }
                    tvBackToStudyFormat.id -> {
                        val pref =
                            SharedPreferences(this@StudyingFragment).initSharedPref(STUDY_FORMAT)
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