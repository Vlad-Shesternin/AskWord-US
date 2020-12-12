package com.veldan.askword_us.dictionary.word_creator

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.database.WordDatabase
import com.veldan.askword_us.database.WordModel
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryFragment
import kotlin.math.log

class WordCreatorDialog(
    private val layoutWordCreation: LayoutWordCreatorBinding,
    private val fragment: DictionaryFragment
) : View.OnClickListener {

    // Components
    private lateinit var viewModel: WordCreatorViewModel

    companion object {
        val translations = mutableListOf<String>()
    }

    // Components UI
    private lateinit var editWord: EditText
    private lateinit var editTranslation: EditText
    private lateinit var editPrompt: EditText

    // init
    init {
        initComponentsUI()
        initListeners()
        initViewModel()
        initAnimator()
    }

    // ==============================
    //    Init Components UI
    // ==============================
    private fun initComponentsUI() {
        layoutWordCreation.also {
            editWord = it.editWord
            editTranslation = it.editTranslation
            editPrompt = it.editPrompt
        }
    }

    // ==============================
    //    Init Listeners
    // ==============================
    private fun initListeners() {
        layoutWordCreation.ibTranslations.setOnClickListener(this)
        fragment.binding.fabAdd.setOnClickListener(this)

        layoutWordCreation.ivImgAdd.setOnClickListener(this)
    }

    // ==============================
    //    Init ViewModel
    // ==============================
    private fun initViewModel() {
        val application = requireNotNull(fragment.activity).application
        val databaseDao = WordDatabase.getInstance(application).wordDatabaseDao

        val viewModelFactory = WordCreatorViewModelFactory(databaseDao, application)
        viewModel = ViewModelProvider(fragment, viewModelFactory)
            .get(WordCreatorViewModel::class.java)
    }

    // ==============================
    //    Init Animator
    // ==============================
    private fun initAnimator() {
        WordCreatorAnimator(layoutWordCreation, fragment)
    }

    // ==============================
    //    Get WordModel
    // ==============================
    private fun getWordModel(): WordModel? {
        // word
        val word = editWord.text.toString()
        // translation
        editTranslation.text.toString().also {
            if (it != "")
                translations.add(it)
        }
        // prompt
        val prompt = editPrompt.text.toString()

        return if (word != "" && translations.isNotEmpty()) {
            WordModel(word = word, translations = translations, prompt = prompt)
        } else {
            null
        }
    }

    private fun clearWordCreator() {
        editWord.text.clear()
        editTranslation.text.clear()
        editPrompt.text.clear()
    }

    // ==============================
    //    OnClick
    // ==============================
    override fun onClick(view: View?) {
        when (view!!.id) {
            fragment.binding.fabAdd.id -> {
                getWordModel()?.let { viewModel.insert(it) }
            }
        }
    }
}

