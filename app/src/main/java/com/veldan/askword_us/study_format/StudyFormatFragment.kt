package com.veldan.askword_us.study_format

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.databinding.FragmentStudyFormatBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_ADDITIONAL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_FILL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_SELECTION
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_PHRASE
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_WORD
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.STUDY_FORMAT
import com.veldan.askword_us.global.general_classes.SharedPreferences as SharePref
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS_PHRASES
import com.veldan.askword_us.global.toast
import kotlinx.android.synthetic.main.fragment_study_format.view.*
import kotlinx.android.synthetic.main.layout_format_addition.view.*
import kotlinx.android.synthetic.main.layout_format_fill.view.*
import kotlinx.android.synthetic.main.layout_format_selection.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyFormatFragment :
    Fragment(),
    View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    private val TAG = this::class.simpleName

    // Binding
    private lateinit var binding: FragmentStudyFormatBinding

    // ComponentsUI
    private lateinit var tvQuestionWord: TextView
    private lateinit var tvQuestionPhrase: TextView
    private lateinit var cbQuestionWord: CheckBox
    private lateinit var cbQuestionPhrase: CheckBox
    private lateinit var constLayAnswerFill: ConstraintLayout
    private lateinit var constLayAnswerSelection: ConstraintLayout
    private lateinit var constLayAnswerAdditional: ConstraintLayout
    private lateinit var cbAnswerFill: CheckBox
    private lateinit var cbAnswerSelection: CheckBox
    private lateinit var cbAnswerAdditional: CheckBox
    private lateinit var fabBack: ImageButton
    private lateinit var fabNext: ImageButton

    // Components
    private lateinit var databaseDao: DatabaseDao
    private lateinit var argsStudyFormat: StudyFormatFragmentArgs
    private val animations = StudyFormatAnimations
    private val animator = Animator2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding = FragmentStudyFormatBinding.inflate(inflater)
    }

    // ==============================
    //    init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            tvQuestionWord = it.tvQuestionWord
            tvQuestionPhrase = it.tvQuestionPhrase
            cbQuestionWord = it.cbQuestionWord
            cbQuestionPhrase = it.cbQuestionPhrase
            fabBack = it.fabBack
            fabNext = it.fabNext
        }

        binding.scrollHAnswers.linearH_answers.also {
            constLayAnswerFill =
                it.layout_format_fill.apply {
                    cbAnswerFill = cb_answer_fill
                } as ConstraintLayout

            constLayAnswerSelection =
                it.layout_format_selection.apply {
                    cbAnswerSelection = cb_answer_selection
                } as ConstraintLayout

            constLayAnswerAdditional =
                it.layout_format_addition.apply {
                    cbAnswerAdditional = cb_answer_additional
                } as ConstraintLayout
        }
    }

    // ==============================
    //    init Listeners
    // ==============================
    private fun initListeners() {
        // onClick
        tvQuestionWord.setOnClickListener(this)
        tvQuestionPhrase.setOnClickListener(this)
        constLayAnswerFill.setOnClickListener(this)
        constLayAnswerSelection.setOnClickListener(this)
        constLayAnswerAdditional.setOnClickListener(this)
        fabBack.setOnClickListener(this)
        fabNext.setOnClickListener(this)

        // onCheck
        cbQuestionWord.setOnCheckedChangeListener(this)
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initAnimator()
        initUI()
        initDao()
    }

    // ==============================
    //    init Animator
    // ==============================
    private fun initAnimator() {
        animator.motion = binding.root
    }

    // ==============================
    //    init UI
    // ==============================
    private fun initUI() {
        animator.apply {
            animations.apply {
                argsStudyFormat = StudyFormatFragmentArgs.fromBundle(requireArguments())
                when (argsStudyFormat.wordsPhrases) {
                    WORDS_PHRASES -> {
                        jumpToState(start_words_phrases)
                    }
                    WORDS -> {
                        jumpToState(start_words)
                    }
                }
            }
        }
    }

    // ==============================
    //    init Dao
    // ==============================
    private fun initDao() {
        val application = requireNotNull(activity).application
        MyDatabase.getInstance(application).also {
            databaseDao = it.databaseDao
        }
    }

    // ==============================
    //    to Study
    // ==============================
    private fun transitionToStudy() {
        CoroutineScope(Dispatchers.IO).launch {
            databaseDao.apply {
                selectedWordsDelete()
                selectedPhrasesDelete()
            }
        }
        findNavController().popBackStack()
    }

    // ==============================
    //    to Studying
    // ==============================
    private fun transitionToStudying() {
        SharePref(this).initSharedPref(STUDY_FORMAT).edit().clear().apply()
        val action = StudyFormatFragmentDirections.actionStudyFormatFragmentToStudyingFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    Set format To SharedPref
    // ==============================
    private fun setFormatToSharedPref() {
        val edit = SharePref(this).initSharedPref(STUDY_FORMAT).edit()
        Log.i(TAG, "setFormatToSharedPref: ddddddddddd === ${cbQuestionPhrase.isChecked}")
        edit.apply {
            when {
                какгого то ХУЯ when виделивается
                cbQuestionWord.isChecked -> {
                    putBoolean(QUESTION_FORMAT_WORD, true)
                    Log.i(TAG, "setFormatToSharedPref: dsfdsgfsasfgfagasfg")
                }
                cbQuestionPhrase.isChecked -> {
                    Log.i(TAG, "setFormatToSharedPref: ssssssssssssssssssssssssss")
                    putBoolean(QUESTION_FORMAT_PHRASE, true)
                }
                cbAnswerFill.isChecked -> putBoolean(ANSWER_FORMAT_FILL, true)
                cbAnswerSelection.isChecked -> putBoolean(ANSWER_FORMAT_SELECTION, true)
                cbAnswerAdditional.isChecked -> putBoolean(ANSWER_FORMAT_ADDITIONAL, true)
            }
        }.apply()
    }

    // ==============================
    //    Get format To SharedPref
    // ==============================
    private fun getFormatToSharedPref(wordsPhrase: Int): Boolean {
        return when (wordsPhrase) {
            WORDS_PHRASES -> {
                return when {
                    !(cbQuestionWord.isChecked) && !(cbQuestionPhrase.isChecked) -> {
                        "Оберiть формат запитання".toast(requireContext())
                        false
                    }
                    cbQuestionWord.isChecked &&
                            !(cbAnswerFill.isChecked) &&
                            !(cbAnswerSelection.isChecked) &&
                            !(cbAnswerAdditional.isChecked) -> {
                        "Оберiть формат вiдповiдi".toast(requireContext())
                        false
                    }
                    else -> true
                }
            }
            WORDS -> {
                return when {
                    !(cbAnswerFill.isChecked) && !(cbAnswerSelection.isChecked) && !(cbAnswerAdditional.isChecked) -> {
                        "Оберiть формат вiдповiдi".toast(requireContext())
                        false
                    }
                    else -> {
                        SharePref(this).initSharedPref(STUDY_FORMAT).edit().putBoolean(
                            QUESTION_FORMAT_WORD, true).apply()
                        true
                    }
                }
            }
            else -> false
        }
    }

    // ==============================
    //    on Click
    // ==============================
    override fun onClick(v: View) {
        when (v.id) {
            tvQuestionWord.id -> {
                cbQuestionWord.apply { isChecked = !isChecked }
            }
            tvQuestionPhrase.id -> {
                cbQuestionPhrase.apply { isChecked = !isChecked }
            }
            constLayAnswerFill.id -> {
                cbAnswerFill.apply { isChecked = !isChecked }
            }
            constLayAnswerSelection.id -> {
                cbAnswerSelection.apply { isChecked = !isChecked }
            }
            constLayAnswerAdditional.id -> {
                cbAnswerAdditional.apply { isChecked = !isChecked }
            }
            fabBack.id -> {
                transitionToStudy()
            }
            fabNext.id -> {
                setFormatToSharedPref()
                if (getFormatToSharedPref(argsStudyFormat.wordsPhrases)) {
                    transitionToStudying()
                }
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        animator.apply {
            animations.apply {
                when (buttonView to isChecked) {
                    cbQuestionWord to true -> transition(start_words_phrases to show_answer_format)
                    cbQuestionWord to false -> {
                        transition(show_answer_format to start_words_phrases)
                        cbAnswerFill.isChecked = false
                        cbAnswerSelection.isChecked = false
                        cbAnswerAdditional.isChecked = false
                    }
                }
            }
        }
    }
}