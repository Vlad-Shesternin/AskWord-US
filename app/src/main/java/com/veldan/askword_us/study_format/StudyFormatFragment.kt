package com.veldan.askword_us.study_format

import android.os.Bundle
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
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.databinding.FragmentStudyFormatBinding
import kotlinx.android.synthetic.main.fragment_study_format.view.*
import kotlinx.android.synthetic.main.layout_format_addition.view.*
import kotlinx.android.synthetic.main.layout_format_fill.view.*
import kotlinx.android.synthetic.main.layout_format_selection.view.*

class StudyFormatFragment : Fragment(), View.OnClickListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initComponentsUI()
        initListeners()

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
                transitionToStudy()
            }
            fabNext.id -> {
                transitionToStudying()
            }
        }
    }
}