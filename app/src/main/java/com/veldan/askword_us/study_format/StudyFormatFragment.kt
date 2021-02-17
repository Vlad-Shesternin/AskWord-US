package com.veldan.askword_us.study_format

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.databinding.FragmentStudyFormatBinding
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.WordsPhrases
import com.veldan.askword_us.global.objects.WordsPhrases.PHRASES
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS_PHRASES
import kotlinx.android.synthetic.main.fragment_study_format.view.*
import kotlinx.android.synthetic.main.layout_format_addition.view.*
import kotlinx.android.synthetic.main.layout_format_fill.view.*
import kotlinx.android.synthetic.main.layout_format_selection.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyFormatFragment : Fragment(), View.OnClickListener {
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
        tvQuestionWord.setOnClickListener(this)
        tvQuestionPhrase.setOnClickListener(this)
        constLayAnswerFill.setOnClickListener(this)
        constLayAnswerSelection.setOnClickListener(this)
        constLayAnswerAdditional.setOnClickListener(this)
        fabBack.setOnClickListener(this)
        fabNext.setOnClickListener(this)
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
        val args = StudyFormatFragmentArgs.fromBundle(requireArguments())
        animator.apply {
            animations.apply {

                when (args.wordsPhrases) {
                    WORDS_PHRASES -> {
                        jumpToState(start_words_phrases)
                    }
                    WORDS -> {
                        jumpToState(start_words)
                    }
                    PHRASES -> {

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
        findNavController().popBackStack()
    }

    // ==============================
    //    to Studying
    // ==============================
    private fun transitionToStudying() {
        val action = StudyFormatFragmentDirections.actionStudyFormatFragmentToStudyingFragment()
        findNavController().navigate(action)
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
                CoroutineScope(Dispatchers.IO).launch {
                    databaseDao.apply {
                        selectedWordsDelete()
                        selectedPhrasesDelete()
                    }
                }
                transitionToStudy()
            }
            fabNext.id -> {
                transitionToStudying()
            }
        }
    }
}