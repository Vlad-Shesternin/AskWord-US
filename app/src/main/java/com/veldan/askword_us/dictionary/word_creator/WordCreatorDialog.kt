package com.veldan.askword_us.dictionary.word_creator

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.R
import com.veldan.askword_us.database.WordDatabase
import com.veldan.askword_us.database.WordModel
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryAnimator
import com.veldan.askword_us.dictionary.DictionaryFragment
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.interfaces.TextChangeListener
import com.veldan.askword_us.global.interfaces.TransitionListener

class WordCreatorDialog(
    private val fragment: DictionaryFragment,
    private val binding: LayoutWordCreatorBinding
) : View.OnClickListener,
    TransitionListener,
    TextChangeListener {

    // Components
    private lateinit var viewModel: WordCreatorViewModel
    private val listTranslations = ListTranslations(fragment.layoutInflater)
    private val translations = mutableListOf<String>()
    private val animator = WordCreatorAnimator

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var editWord: EditText
    private lateinit var editPrompt: EditText
    private lateinit var ifvPromptAdd: ImageFilterView
    private lateinit var tvTranslation: TextView
    private lateinit var ibTranslation: ImageButton
    private lateinit var editTranslation: EditText
    private lateinit var ifvListTranslation: ImageFilterView
    private lateinit var layoutTranslations: ConstraintLayout

    // init
    init {
        initViewModel()
        initComponentsUI()
        initComponents()
        initListeners()
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
    //    Init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motion = it.motionWordCreator
            editWord = it.editWord
            editPrompt = it.editPrompt
            ifvPromptAdd = it.ifvPromptAdd
            tvTranslation = it.tvTranslation
            ibTranslation = it.ibTranslations
            editTranslation = it.editTranslation
            ifvListTranslation = it.ifvListTranslations
            layoutTranslations = it.layoutTranslations.layoutTranslations
        }
    }

    // ==============================
    //    Init Components
    // ==============================
    private fun initComponents() {
        WordCreatorAnimator.motion = this.motion
    }

    // ==============================
    //    Init Listeners
    // ==============================
    private fun initListeners() {
        // onClick
        ifvPromptAdd.setOnClickListener(this)
        ibTranslation.setOnClickListener(this)
        ifvListTranslation.setOnClickListener(this)
        // onTransition
        motion.setTransitionListener(this)
        // onChangeText
        editTranslation.addTextChangedListener(this)
    }

//
//    // ==============================
//    //    Get WordModel
//    // ==============================
//    private fun getWordModel(): WordModel? {
//        // word
//        val word = editWord.text.toString()
//        // translation
//        editTranslation.text.toString().also {
//            if (it != "")
//                translations.add(it)
//        }
//        // prompt
//        val prompt = editPrompt.text.toString()
//
//        return if (word != "" && translations.isNotEmpty()) {
//            WordModel(word = word, translations = translations, prompt = prompt)
//        } else {
//            null
//        }
//    }

    // ==============================
    //    Add Translation
    // ==============================
    private fun addTranslation(): Boolean {
        editTranslation.also { edit ->
            val editText = edit.text.toString()
            if (editText != "") {
                translations.add(editText)
                listTranslations.addItemToLayoutTranslations(editText)
                tvTranslation.text = editText
                edit.hint = ""
                edit.text.clear()
                return true
            }
            return false
        }
    }

    // ==============================
    //    After end set
    // ==============================
    private fun afterEndSet_2() {
        editPrompt.defaultFocusAndKeyboard(true)
    }

    private fun afterEndSet_4() {
        tvTranslation.text = ""
        editTranslation.hint =
            fragment.requireContext().getString(R.string.word_creation_edit_enter_translation)
        motion.progress = 0F
    }

    // ==============================
    //    OnClick
    // ==============================
    override fun onClick(view: View?) {
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (view!!.id to motion.currentState) {
            ifvPromptAdd.id to animator.start -> animator.start_To_Set_1()
            ifvPromptAdd.id to animator.set_3 -> animator.set_3_To_Set_1()

            ibTranslation.id to animator.set_3 -> if (addTranslation()) {
                animator.set_3_To_Set_4()
            }

            ifvListTranslation.id to animator.set_3 -> if (translations.isNotEmpty()) {
                animator.set_3_To_Set_5()
            }
            ifvListTranslation.id to animator.set_5 -> animator.set_5_To_Set_3()
        }
    }

    // ==============================
    //    OnTransitionChange
    // ==============================
    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        start: Int,
        end: Int,
        progress: Float
    ) {
        super.onTransitionChange(motionLayout, start, end, progress)
        when (end to progress) {
            animator.set_2 to 1.0f ->
                afterEndSet_2()
            animator.set_4 to 1.0f -> {
                afterEndSet_4()
            }
        }
    }

    // ==============================
    //    AfterTextChanged
    // ==============================
    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)

        if (translations.isEmpty()) {
            when (s!!.length) {
                1 -> animator.start_To_Set_3()

                0 -> animator.set_3_To_Start()

            }
        }
    }
}

