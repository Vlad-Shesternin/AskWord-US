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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.study.adapters.PhraseAdapter
import com.veldan.askword_us.study.adapters.WordAdapter

class StudyFragment :
    Fragment(),
    View.OnClickListener {

    private val TAG = this::class.simpleName

    // Binding
    private lateinit var binding: FragmentStudyBinding

    // ComponentsUI
    private lateinit var motion: MotionLayout
    private lateinit var tabWord: Button
    private lateinit var tabPhrase: Button
    private lateinit var fabAdd: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var rvWords: RecyclerView
    private lateinit var rvPhrases: RecyclerView
    private lateinit var tvCountSelectedWords: TextView

    // Components
    private lateinit var wordDatabaseDao: WordDatabaseDao
    private lateinit var phraseDatabaseDao: PhraseDatabaseDao
    private lateinit var adapterWord: WordAdapter
    private lateinit var adapterPhrase: PhraseAdapter
    private val animator = StudyAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initComponentsUI()
        initComponents()
        initListeners()

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
            motion = it.root
            tabWord = it.tabs.btnWords
            tabPhrase = it.tabs.btnPhrases
            fabAdd = it.fabAdd
            fabBack = it.fabBack
            rvWords = it.rvListWords
            rvPhrases = it.layoutPhrases.rvListPhrases
            tvCountSelectedWords = it.tvCountSelectedWords
        }
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initDao()
        initAnimators()
        initAdapters()
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
    private fun initAnimators() {
        animator.motion = motion
    }

    // ==============================
    //    init Adapters
    // ==============================
    private fun initAdapters() {
        // Adapter Word
        adapterWord = WordAdapter(animator, binding)
        wordDatabaseDao.getAllWords()?.observe(viewLifecycleOwner, Observer {
            adapterWord.words = it
        })
        rvWords.adapter = adapterWord

        // Adapter Phrase
        adapterPhrase = PhraseAdapter(animator, binding)
        phraseDatabaseDao.getAllPhrase()?.observe(viewLifecycleOwner, Observer {
            adapterPhrase.phrases = it
        })
        rvPhrases.adapter = adapterPhrase
    }

    // ==============================
    //    to Dictionary or Study
    // ==============================
    private fun transitionToDictionaryOrStudy() {
        findNavController().popBackStack()
    }

    // ==============================
    //    on Click
    // ==============================
    override fun onClick(v: View) {
        when (v.id) {
            tabWord.id -> {
                resources.apply {
                    tabWord.background = getDrawable(R.drawable.tab_press)
                    tabPhrase.background = getDrawable(R.drawable.tab_enable)
                }
                animator.apply {
                    when (motion.currentState) {
                        show_list_phrases -> showListPhrases_To_Start()

                    }
                }
            }
            tabPhrase.id -> {
                resources.apply {
                    tabPhrase.background = getDrawable(R.drawable.tab_press)
                    tabWord.background = getDrawable(R.drawable.tab_enable)
                }
                animator.apply {
                    when (motion.currentState) {
                        start -> start_To_ShowListPhrases()

                    }
                }
            }
            fabBack.id -> {
                animator.apply {
                    when (motion.currentState) {
                        show_detailed_info_word -> {
                            showDetailedInfoWord_To_Start()
                        }
                        show_detailed_info_phrase -> {
                            showDetailedInfoPhrase_To_ShowListPhrases()
                        }
                        else -> transitionToDictionaryOrStudy()
                    }
                }
            }
        }
    }
}