package com.veldan.askword_us.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimations.show_list_phrases
import com.veldan.askword_us.study.adapters.PhraseAdapter
import com.veldan.askword_us.study.adapters.WordAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyFragment :
    Fragment(),
    View.OnClickListener {

    private val TAG = this::class.simpleName

    // Binding
    private lateinit var binding: FragmentStudyBinding

    // ComponentsUI
    private lateinit var motionStudy: MotionLayout
    private lateinit var motionCounter: MotionLayout
    private lateinit var tabWord: Button
    private lateinit var tabPhrase: Button
    private lateinit var fabAdd: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var rvWords: RecyclerView
    private lateinit var rvPhrases: RecyclerView
    private lateinit var tvCountSelectedWords: TextView
    private lateinit var tvCountSelectedPhrases: TextView

    // Components
    private lateinit var wordDatabaseDao: WordDatabaseDao
    private lateinit var phraseDatabaseDao: PhraseDatabaseDao
    private lateinit var adapterWord: WordAdapter
    private lateinit var adapterPhrase: PhraseAdapter
    private val animations = StudyAnimations
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
        binding = FragmentStudyBinding.inflate(inflater)
    }

    // ==============================
    //    init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motionStudy = it.root
            tabWord = it.tabs.btnWords
            tabPhrase = it.tabs.btnPhrases
            fabAdd = it.fabAdd
            fabBack = it.fabBack
            rvWords = it.rvListWords
            rvPhrases = it.layoutPhrases.rvListPhrases
        }
        binding.layoutCountsSelectedWp.also {
            motionCounter = it.root
            tvCountSelectedWords = it.tvCountSelectedWords
            tvCountSelectedPhrases = it.tvCountSelectedPhrases
        }
    }

    // ==============================
    //    init Listeners
    // ==============================
    private fun initListeners() {
        tabWord.setOnClickListener(this)
        tabPhrase.setOnClickListener(this)
        fabAdd.setOnClickListener(this)
        fabBack.setOnClickListener(this)
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initDao()
        initAnimator()
        initAdapters()
    }

    // ==============================
    //    init Dao
    // ==============================
    private fun initDao() {
        val application = requireNotNull(activity).application
        MyDatabase.getInstance(application).also {
            wordDatabaseDao = it.wordDatabaseDao
            phraseDatabaseDao = it.phraseDatabaseDao
        }
    }

    // ==============================
    //    init Animators
    // ==============================
    private fun initAnimator() {
        animator.motion = motionStudy
    }

    // ==============================
    //    init Adapters
    // ==============================
    private fun initAdapters() {
        // Adapter Word
        adapterWord = WordAdapter(animations, binding)
        wordDatabaseDao.getAllWords()?.observe(viewLifecycleOwner, Observer {
            adapterWord.words = it
        })
        rvWords.adapter = adapterWord

        // Adapter Phrase
        adapterPhrase = PhraseAdapter(animations, binding)
        phraseDatabaseDao.getAllPhrase()?.observe(viewLifecycleOwner, Observer {
            adapterPhrase.phrases = it
        })
        rvPhrases.adapter = adapterPhrase
    }

    // ==============================
    //    to Dictionary or Study
    // ==============================
    private fun transitionToDictionaryOrStudy() {
        Animator2.previous.clear()
        findNavController().popBackStack()
    }

    // ==============================
    //    Click Tab Word
    // ==============================
    private fun clickTabWord() {
        resources.apply {
            tabWord.background = getDrawable(R.drawable.tab_press)
            tabPhrase.background = getDrawable(R.drawable.tab_enable)
        }
    }

    // ==============================
    //    Click Tab Phrase
    // ==============================
    private fun clickTabPhrase() {
        resources.apply {
            tabPhrase.background = getDrawable(R.drawable.tab_press)
            tabWord.background = getDrawable(R.drawable.tab_enable)
        }
    }

    // ==============================
    //    on Click
    // ==============================
    override fun onClick(v: View) {
        when (v.id) {
            // ==============================
            //    TabWord
            // ==============================
            tabWord.id -> {
                animator.apply {
                    this.motion = motionStudy
                    animations.apply {
                        when (motion.currentState) {
                            show_list_phrases -> {
                                clickTabWord()
                                transition(show_list_phrases to start)
                                motion = motionCounter
                                if (motion.currentState == show_count_selected_phrases
                                    && previous.contains(show_count_selected_words)
                                ) {
                                    transition(show_count_selected_phrases to show_count_selected_words)
                                } else {
                                    transition(motion.currentState to start)
                                }
                            }
                        }
                    }
                }
            }
            // ==============================
            //    TabPhrase
            // ==============================
            tabPhrase.id -> {
                animator.apply {
                    this.motion = motionStudy
                    animations.apply {
                        when (motion.currentState) {
                            start -> {
                                clickTabPhrase()
                                transition(start to show_list_phrases)
                                motion = motionCounter
                                if (motion.currentState == show_count_selected_words
                                    && previous.contains(show_count_selected_phrases)
                                ) {
                                    transition(show_count_selected_words to show_count_selected_phrases)
                                } else {
                                    transition(motion.currentState to start)
                                }
                            }
                        }
                    }
                }
            }
            // ==============================
            //    FabBack
            // ==============================
            fabBack.id -> {
                animator.apply {
                    this.motion = motionStudy
                    animations.apply {
                        when (motion.currentState) {
                            show_detailed_info_word -> {
                                transition(show_detailed_info_word to start)
                                if (previous.contains(show_count_selected_words)) {
                                    motion = motionCounter
                                    transition(start to show_count_selected_words)
                                }
                            }
                            show_detailed_info_phrase -> {
                                transition(show_detailed_info_phrase to show_list_phrases)
                                if (previous.contains(show_count_selected_phrases)) {
                                    motion = motionCounter
                                    transition(start to show_count_selected_phrases)
                                }
                            }
                            else -> transitionToDictionaryOrStudy()
                        }
                    }
                }
            }
        }
    }
}