package com.veldan.askword_us.studying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.databinding.FragmentStudyingBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_ADDITIONAL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_FILL
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.ANSWER_FORMAT_SELECTION
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_PHRASE
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_WORD
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.STUDY_FORMAT
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StudyingFragment :
    Fragment(),
    View.OnClickListener {
    private val TAG = this::class.simpleName

    // Binding
    private lateinit var binding: FragmentStudyingBinding

    // ComponentsUI
    private lateinit var fabSettings: ImageButton
    private lateinit var fabNext: ImageButton
    private lateinit var tvBackToStudyFormat: TextView
    private lateinit var tvBackToStudy: TextView
    private lateinit var constraintLayoutWord: ConstraintLayout

    // Components
    private lateinit var databaseDao: DatabaseDao
    private val animations = StudyingAnimations
    private val animator = Animator2

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
            constraintLayoutWord = it.layoutStudyingWordFirst.root
        }
    }

    // ==============================
    //    init Listeners
    // ==============================
    private fun initListeners() {
        fabSettings.setOnClickListener(this)
        fabNext.setOnClickListener(this)
        tvBackToStudy.setOnClickListener(this)
        tvBackToStudyFormat.setOnClickListener(this)
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
        val pref = SharedPreferences(this).initSharedPref(STUDY_FORMAT)
        animator.apply {
            animations.apply {
                when {
                    pref.getBoolean(QUESTION_FORMAT_WORD, false) &&
                            pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                        "Word ---------------- Phrase".toast(requireContext())
                        answerFormat(pref)
                    }
                    pref.getBoolean(QUESTION_FORMAT_WORD, false) -> {
                        "Word".toast(requireContext())
                        jumpToState(start_word)
                        answerFormat(pref)
                    }
                    pref.getBoolean(QUESTION_FORMAT_PHRASE, false) -> {
                        "Phrase".toast(requireContext())
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
    //    init Dao
    // ==============================
    private fun initDao() {
        val application = requireNotNull(activity).application
        MyDatabase.getInstance(application).also {
            databaseDao = it.databaseDao
        }
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
                        when (motion.currentState) {
                            start, start_word -> {
                                transition(motion.currentState to show_settings)
                            }
                            show_settings -> {
                                transition(show_settings to start_word)
                            }
                        }
                    }
                    fabNext.id -> {
                        //transition(start to next_word)
                    }
                    tvBackToStudy.id -> {
                        transitionToStudy()
                    }
                    tvBackToStudyFormat.id -> {
                        transitionToStudyFormat()
                    }
                }
            }
        }
    }
}