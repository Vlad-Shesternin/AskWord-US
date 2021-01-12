package com.veldan.askword_us.dictionary.word_creator

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.camera.view.PreviewView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.MainActivity
import com.veldan.askword_us.R
import com.veldan.askword_us.database.WordDatabase
import com.veldan.askword_us.database.WordModel
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryAnimator
import com.veldan.askword_us.dictionary.DictionaryFragment
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.general_classes.Camera
import com.veldan.askword_us.global.interfaces.TextChangeListener
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.RequestCode
import com.veldan.askword_us.global.toast

class WordCreatorDialog(
    private val fragment: DictionaryFragment,
    private val binding: LayoutWordCreatorBinding,
) : View.OnClickListener,
    TransitionListener,
    TextChangeListener, View.OnLongClickListener {

    // ViewModel
    private lateinit var viewModel: WordCreatorViewModel

    // Animators
    private val animator = WordCreatorAnimator
    private val animatorDictionary = DictionaryAnimator

    // Components
    private val TAG = this::class.simpleName
    private val listTranslations = ListTranslations(fragment.layoutInflater)

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var fabAdd: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var preview: PreviewView
    private lateinit var editWord: EditText
    private lateinit var ivImgAdd: ImageView
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
        initBinding()
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
    //    Init Binding
    // ==============================
    private fun initBinding() {
        binding.dialogWordCreator = this
    }


    // ==============================
    //    Init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motion = it.motionWordCreator
            fabAdd = it.fabAdd
            fabBack = it.fabBack
            preview = it.preview
            editWord = it.editWord
            ivImgAdd = it.ivImgAdd
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
        fabBack.setOnClickListener(this)
        ivImgAdd.setOnClickListener(this)
        ifvPromptAdd.setOnClickListener(this)
        ibTranslation.setOnClickListener(this)
        ifvListTranslation.setOnClickListener(this)
        // onTransition
        motion.setTransitionListener(this)
        // onChangeText
        editTranslation.addTextChangedListener(this)


        fabAdd.setOnLongClickListener(this)
    }

    // ==============================
    //    Add Translation
    // ==============================
    private fun addTranslation(): Boolean {
        editTranslation.also { edit ->
            val editText = edit.text.toString()
            if (editText != "") {
                listTranslations.addItemToLayoutTranslations(editText, layoutTranslations)
                tvTranslation.text = editText
                edit.hint = ""
                edit.text.clear()
                return true
            }
            return false
        }
    }

    // ==============================
    //    Get WordModel
    // ==============================
    private fun getWordModel(): WordModel? {
        val word = editWord.text.toString()
        val prompt = editPrompt.text.toString()
        val translation = editTranslation.text.toString()
        val translations = listTranslations.listTranslations.apply { add(translation) }

        return if (word != "") {
            WordModel(
                word = word,
                translations = translations,
                prompt = prompt,
            )
        } else {
            "Введите Слово".toast(fragment.requireContext())
            null
        }
    }

    // ==============================
    //    Insert to DB
    // ==============================
    fun insert() {
        val wordModel = getWordModel()
        wordModel?.let {
            viewModel.insert(it)
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
    //    onClick
    // ==============================
    override fun onClick(view: View) {
        when (view.id) {
            ivImgAdd.id -> {
                val camera = Camera(fragment)
                camera.startCamera(preview)
                when (motion.currentState) {
                    animator.start -> animator.start_To_Set_6()
                    animator.set_3 -> animator.set_3_To_Set_6()
                }
            }
        }

        // Animation
        // when use Pair<Int, Int>(v?.id, motion.currentState)
        when (view.id to motion.currentState) {
            ifvPromptAdd.id to animator.start -> {
                animator.start_To_Set_1()
            }
            ifvPromptAdd.id to animator.set_3 -> {
                animator.set_3_To_Set_1()
            }
            ibTranslation.id to animator.set_3 -> {
                if (addTranslation())
                    animator.set_3_To_Set_4()
            }
            ifvListTranslation.id to animator.set_3 -> {
                if (listTranslations.listTranslations.isNotEmpty())
                    animator.set_3_To_Set_5()
            }
            ifvListTranslation.id to animator.set_5 -> {
                animator.set_5_To_Set_3()
            }
            fabBack.id to animator.start -> {
                animatorDictionary.set_6_To_Start()
            }
            fabBack.id to animator.set_2 -> {
                animator.set_2_To_Set_1()
            }
        }
    }

    // ==============================
    //    onTransitionChange
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {
        super.onTransitionCompleted(motionLayout, end)

        when (motionLayout!!.startState to end) {
            animator.start to animator.set_1 -> {
                animator.set_1_To_Set_2()
            }
            animator.set_1 to animator.set_2 -> {
                afterEndSet_2()
            }
            animator.set_2 to animator.set_1 -> {
                if (listTranslations.listTranslations.isEmpty()
                    && editTranslation.text.toString() == ""
                ) {
                    animator.set_1_To_Start()
                } else {
                    animator.set_1_To_Set_3()
                }
            }
            animator.set_3 to animator.set_1 -> {
                animator.set_1_To_Set_2()
            }
            animator.set_3 to animator.set_4 -> {
                afterEndSet_4()
            }
            animator.set_5 to animator.set_3 -> {
                if (listTranslations.listTranslations.isEmpty())
                    animator.set_3_To_Start()
            }
        }
    }

    // ==============================
    //    afterTextChanged
    // ==============================
    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)

        if (listTranslations.listTranslations.isEmpty()) {
            when (s!!.length) {
                1 -> animator.start_To_Set_3()
                0 -> animator.set_3_To_Start()
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
//        val db = viewModel.words
//        if (db != null) {
//            Log.i(TAG, "onLongClick: ${db.value}")
//        } else {
//            Log.i(TAG, "onLongClick: nuuuuuul")
//        }
        return true
    }
}

