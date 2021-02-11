package com.veldan.askword_us.study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.databinding.FragmentStudyBinding

class StudyFragment :
    Fragment(),
    View.OnClickListener {

    // Binding
    private lateinit var binding: FragmentStudyBinding

    // ComponentsUI
    private lateinit var tabWord: Button
    private lateinit var tabPhrase: Button
    private lateinit var fabAdd: ImageButton
    private lateinit var fabBack: ImageButton

    // Components
    private lateinit var wordDatabaseDao: WordDatabaseDao
    private lateinit var phraseDatabaseDao: PhraseDatabaseDao
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
            tabWord = it.tabs.btnWords
            tabPhrase = it.tabs.btnPhrases
            fabAdd = it.fabAdd
            fabBack = it.fabBack
        }
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initDao()
        initAnimators()
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
        animator.motion = binding.root
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
            }
            tabPhrase.id -> {
                resources.apply {
                    tabPhrase.background = getDrawable(R.drawable.tab_press)
                    tabWord.background = getDrawable(R.drawable.tab_enable)
                }
            }
            fabBack.id -> {
                transitionToDictionaryOrStudy()
            }
        }
    }
}