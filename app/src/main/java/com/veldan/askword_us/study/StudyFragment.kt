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
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseDatabaseDao
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseModel
import com.veldan.askword_us.database.selected_word.SelectedWordDatabaseDao
import com.veldan.askword_us.database.selected_word.SelectedWordModel
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_PHRASE
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.QUESTION_FORMAT_WORD
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.STUDY_FORMAT
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.global.objects.WordsPhrases
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS
import com.veldan.askword_us.global.objects.WordsPhrases.WORDS_PHRASES
import com.veldan.askword_us.global.toast
import com.veldan.askword_us.start.StartFragmentDirections
import com.veldan.askword_us.study.StudyAnimations.show_list_phrases
import com.veldan.askword_us.study.adapters.PhraseAdapter
import com.veldan.askword_us.study.adapters.SelectedPhraseAdapter
import com.veldan.askword_us.study.adapters.SelectedWordAdapter
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
    private lateinit var selectedWordDatabaseDao: SelectedWordDatabaseDao
    private lateinit var selectedPhraseDatabaseDao: SelectedPhraseDatabaseDao
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
        tvCountSelectedWords.setOnClickListener(this)
        tvCountSelectedPhrases.setOnClickListener(this)
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
            selectedWordDatabaseDao = it.selectedWordDatabaseDao
            selectedPhraseDatabaseDao = it.selectedPhraseDatabaseDao
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
        val action = StudyFragmentDirections.actionStudyFragmentToDictionaryOrStudyFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    to Study Format
    // ==============================
    private fun transitionToStudyFormat(wordsPhrases: Int) {
        Animator2.previous.clear()
        val action = StudyFragmentDirections.actionStudyFragmentToStudyFormatFragment(wordsPhrases)
        findNavController().navigate(action)
    }

    // ==============================
    //    to Studying
    // ==============================
    private fun transitionToStudying() {
        val action = StudyFragmentDirections.actionStudyFragmentToStudyingFragment()
        findNavController().navigate(action)
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
    //    insert SelectedWords
    // ==============================
    private fun insertSelectedWords(listSelectedWords: MutableList<WordModel>) {
        listSelectedWords.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                selectedWordDatabaseDao.insert(SelectedWordModel(
                    image = it.image,
                    word = it.word,
                    translations = it.translations,
                    prompt = it.prompt
                ))
            }
        }
    }

    // ==============================
    //    insert SelectedPhrases
    // ==============================
    private fun insertSelectedPhrases(listSelectedPhrases: MutableList<PhraseModel>) {
        listSelectedPhrases.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                selectedPhraseDatabaseDao.insert(SelectedPhraseModel(
                    phrase = it.phrase,
                    translation = it.translation
                ))
            }
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
                                } else if (previous.contains(show_count_selected_words)) {
                                    transition(start to show_count_selected_words)
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
                                } else if (previous.contains(show_count_selected_phrases)) {
                                    transition(start to show_count_selected_phrases)
                                } else {
                                    transition(motion.currentState to start)
                                }
                            }
                        }
                    }
                }
            }
            // ==============================
            //    TextView counter word
            // ==============================
            tvCountSelectedWords.id -> {
                animator.apply {
                    this.motion = motionStudy
                    animations.apply {
                        when (motion.currentState) {
                            start -> {
                                SelectedWordAdapter(adapterWord, animations, binding).apply {
                                    words = adapterWord.listSelectedWords
                                    binding.layoutSelectedWords.rvListSelectedWords.adapter = this
                                }
                                transition(start to show_selected_words)
                            }
                        }
                    }
                }
            }
            // ==============================
            //    TextView counter phrase
            // ==============================
            tvCountSelectedPhrases.id -> {
                animator.apply {
                    this.motion = motionStudy
                    animations.apply {
                        when (motion.currentState) {
                            show_list_phrases -> {
                                SelectedPhraseAdapter(adapterPhrase, animations, binding).apply {
                                    phrases = adapterPhrase.listSelectedPhrases
                                    binding.layoutSelectedPhrases.rvListSelectedPhrases.adapter =
                                        this
                                }
                                transition(show_list_phrases to show_selected_phrases)
                            }
                        }
                    }
                }
            }
            // ==============================
            //    FabAdd
            // ==============================
            fabAdd.id -> {
                adapterWord.listSelectedWords.also { listSelectedWords ->
                    adapterPhrase.listSelectedPhrases.also { listSelectedPhrases ->
                        if (listSelectedWords.isNotEmpty() && listSelectedPhrases.isNotEmpty()) {
                            insertSelectedWords(listSelectedWords)
                            insertSelectedPhrases(listSelectedPhrases)
                            transitionToStudyFormat(WORDS_PHRASES)
                            animator.previous.clear()
                            return
                        } else if (listSelectedWords.isNotEmpty()) {
                            insertSelectedWords(listSelectedWords)
                            transitionToStudyFormat(WORDS)
                            animator.previous.clear()
                            return
                        } else if (listSelectedPhrases.isNotEmpty()) {
                            insertSelectedPhrases(listSelectedPhrases)
                            SharedPreferences(this).initSharedPref(STUDY_FORMAT).edit().putBoolean(
                                QUESTION_FORMAT_PHRASE, true).apply()
                            transitionToStudying()
                            animator.previous.clear()
                            return
                        } else {
                            "Оберiть слова або фрази".toast(requireContext())
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
                            show_selected_words -> {
                                transition(show_selected_words to start)
                            }
                            show_selected_phrases -> {
                                transition(show_selected_phrases to show_list_phrases)
                            }
                            show_detailed_info_word_selected -> {
                                transition(show_detailed_info_word_selected to show_selected_words)
                            }
                            show_detailed_info_phrase_selected -> {
                                transition(show_detailed_info_phrase_selected to show_selected_phrases)
                            }
                            else -> transitionToDictionaryOrStudy()
                        }
                    }
                }
            }
        }
    }
}