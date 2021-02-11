package com.veldan.askword_us.dictionary.word_creator

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.camera.view.PreviewView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.word.WordModel
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
    TextChangeListener {

    // ViewModel
    private lateinit var viewModel: WordCreatorViewModel

    // Animators
    private val animator = WordCreatorAnimator
    private val animatorDictionary = DictionaryAnimator

    // Components
    private val TAG = this::class.simpleName
    var savedUri: Uri? = null
    private val listTranslations = ListTranslations(fragment.layoutInflater)

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var fabAdd: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var preview: PreviewView
    private lateinit var editWord: EditText
    private lateinit var ivImgAdd: ImageView
    private lateinit var ivCapture: ImageView
    private lateinit var ivGallery: ImageView
    private lateinit var editPrompt: EditText
    private lateinit var ifvPromptAdd: ImageFilterView
    private lateinit var ibTranslation: ImageButton
    private lateinit var tvTranslation: TextView
    private lateinit var ivCameraClick: ImageView
    private lateinit var ivCheckCapture: ImageView
    private lateinit var ivCancelCapture: ImageView
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
        val databaseDao = MyDatabase.getInstance(application).wordDatabaseDao

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
            ivCapture = it.ivCapture
            ivGallery = it.ivGallery
            editPrompt = it.editPrompt
            ifvPromptAdd = it.ifvPromptAdd
            ivCameraClick = it.ivCameraClick
            tvTranslation = it.tvTranslation
            ibTranslation = it.ibTranslations
            ivCheckCapture = it.ivCheckCapture
            ivCancelCapture = it.ivCancelCapture
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
        fabAdd.setOnClickListener(this)
        fabBack.setOnClickListener(this)
        ivImgAdd.setOnClickListener(this)
        ivCapture.setOnClickListener(this)
        ifvPromptAdd.setOnClickListener(this)
        ibTranslation.setOnClickListener(this)
        ifvListTranslation.setOnClickListener(this)
        // onTransition
        motion.setTransitionListener(this)
        // onChangeText
        editPrompt.addTextChangedListener(this)
        editTranslation.addTextChangedListener(this)
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
        val image = savedUri ?: ""
        val word = editWord.text.toString()
        val translation = editTranslation.text.toString()
        val translations = listTranslations.listTranslations.apply {
            if (editTranslation.text.toString() != "")
                add(translation)
        }
        val prompt = editPrompt.text.toString()

        return if (word != "" && translations.isNotEmpty()) {
            Log.i(TAG, "IF: $translations")
            WordModel(
                image = image.toString(),
                word = word,
                translations = translations,
                prompt = prompt,
            )
        } else {
            "Введите Слово и Перевод".toast(fragment.requireContext())
            null
        }
    }

    // ==============================
    //    Insert to DB
    // ==============================
    private fun insert() {
        val wordModel = getWordModel()
        wordModel?.let {
            Log.i(TAG, "insert: ${wordModel.translations}")
            viewModel.insert(it)
            clearAll()
        }
    }

    // ==============================
    //    ClearAll from WordCreatorDialog
    // ==============================
    private fun clearAll() {
        WordCreatorAnimator.motion.transitionToState(WordCreatorAnimator.start)
        ivCapture.setImageResource(0)

        savedUri = null
        layoutTranslations.removeAllViews()
        editPrompt.text.clear()
        editTranslation.text.clear()
        editWord.text.clear()
        listTranslations.listTranslations.clear()
    }


    // ==============================
    //    After end set
    // ==============================
    private fun afterEndSet_2_15() {
        editPrompt.defaultFocusAndKeyboard(true)
    }

    private fun afterEndSet_4_12() {
        tvTranslation.text = ""
        editTranslation.hint =
            fragment.requireContext().getString(R.string.word_creation_edit_enter_translation)
        motion.progress = 0F
    }

    private fun openCamera() {
        ivCapture.setImageResource(0)
        val camera = Camera(fragment)
        camera.startCamera(preview)

        animator.apply {
            when (motion.currentState) {
                start,
                set_10,
                -> {
                    motion.transitionToState(start)
                    start_To_Set_7()
                }
                set_3,
                set_11,
                -> {
                    motion.transitionToState(set_3)
                    set_3_To_Set_8()
                }
            }
        }

        ivGallery.setOnClickListener {
            val gallery = Intent()
            gallery.type = "image/*"
            gallery.action = Intent.ACTION_GET_CONTENT

            fragment.startActivityForResult(Intent.createChooser(gallery,
                "Select Picture"),
                RequestCode.RC_PICK_IMAGE)
        }

        ivCameraClick.setOnClickListener {
            val liveDataUri = camera.takePhoto()
            liveDataUri.observe(fragment, Observer<Uri> {
                Glide.with(fragment)
                    .load(it)
                    .into(ivCapture)
                savedUri = it
            })
            animator.apply {
                when (motion.currentState) {
                    set_7 -> {
                        trigger = set_7
                        set_7_To_Set_9()
                    }
                    set_8 -> {
                        trigger = set_8
                        set_8_To_Set_9()
                    }
                }
            }

            ivCheckCapture.setOnClickListener {
                animator.apply {
                    when (trigger) {
                        set_7 -> set_9_To_Set_10()
                        set_8 -> set_9_To_Set_11()
                    }
                }
            }

            ivCancelCapture.setOnClickListener {
                camera.deleteImage(savedUri!!.path!!)
                ivCapture.setImageResource(0)
                animator.apply {
                    when (trigger) {
                        set_7 -> set_9_To_Set_7()
                        set_8 -> set_9_To_Set_8()
                    }
                }
            }
        }
    }

    // ==============================
    //    onClick
    // ==============================
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(view: View) {
        when (view.id) {
            fabAdd.id -> {
                animator.apply {
                    when (motion.currentState) {
                        set_3, set_11 -> {
                            insert()
                        }
                        set_16 -> set_16_To_Set_2()
                        set_17 -> set_17_To_Set_15()
                    }
                }
            }

            ivImgAdd.id -> openCamera()
            ivCapture.id -> {
                animator.apply {
                    if (motion.currentState == set_10 || motion.currentState == set_11) {
                        openCamera()
                    }
                }
            }

            ifvPromptAdd.id -> {
                animator.apply {
                    when (motion.currentState) {
                        start -> start_To_Set_1()
                        set_3 -> set_3_To_Set_1()
                        set_10 -> set_10_To_Set_14()
                        set_11 -> set_11_To_Set_14()
                    }
                }
            }
            ifvListTranslation.id -> {
                animator.apply {
                    when (motion.currentState) {
                        set_3 -> {
                            if (listTranslations.listTranslations.isNotEmpty()) {
                                set_3_To_Set_5()
                            }
                        }
                        set_5 -> set_5_To_Set_3()
                        set_11 -> {
                            if (listTranslations.listTranslations.isNotEmpty()) {
                                set_11_To_Set_13()
                            }
                        }
                        set_13 -> set_13_To_Set_11()
                    }
                }
            }
            fabBack.id -> {
                animator.apply {
                    when (motion.currentState) {
                        start, set_3, set_10, set_11 -> {
                            animatorDictionary.set_6_To_Start()
                            clearAll()
                        }
                        set_2 -> set_2_To_Set_1()
                        set_7 -> set_7_To_Start()
                        set_8 -> set_8_To_Set_3()
                        set_15 -> set_15_To_Set_14()
                        set_16 -> {
                            set_16_To_Set_2()
                            editPrompt.text.clear()
                        }
                        set_17 -> {
                            set_17_To_Set_15()
                            editPrompt.text.clear()
                        }
                    }
                }
            }
            ibTranslation.id -> {
                animator.apply {
                    when (motion.currentState) {
                        set_3 -> {
                            if (addTranslation()) {
                                set_3_To_Set_4()
                            }
                        }
                        set_11 -> {
                            if (addTranslation()) {
                                set_11_To_Set_12()
                            }
                        }
                    }
                }
            }
        }
    }

    // ==============================
    //    onTransitionChange
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {
        super.onTransitionCompleted(motionLayout, end)

        animator.apply {
            when (motionLayout!!.startState to end) {
                start to set_1,
                set_3 to set_1,
                -> {
                    set_1_To_Set_2()
                }
                set_10 to set_14,
                set_11 to set_14,
                -> {
                    set_14_To_Set_15()
                }
                set_1 to set_2,
                set_14 to set_15,
                -> {
                    afterEndSet_2_15()
                }

                set_2 to set_1 -> {
                    if (listTranslations.listTranslations.isEmpty()
                        && editTranslation.text.toString() == ""
                    ) {
                        set_1_To_Start()
                    } else {
                        set_1_To_Set_3()
                    }
                }
                set_15 to set_14 -> {
                    if (listTranslations.listTranslations.isEmpty()
                        && editTranslation.text.toString() == ""
                    ) {
                        set_14_To_Set_10()
                    } else {
                        set_14_To_Set_11()
                    }
                }
                set_16 to set_2 -> set_2_To_Set_1()
                set_17 to set_15 -> set_15_To_Set_14()

                set_3 to set_4,
                set_11 to set_12,
                -> {
                    afterEndSet_4_12()
                }
                set_5 to set_3 -> {
                    if (listTranslations.listTranslations.isEmpty())
                        set_3_To_Start()
                }
                set_13 to set_11 -> {
                    if (listTranslations.listTranslations.isEmpty())
                        set_11_To_Set_10()
                }
            }
        }
    }

    // ==============================
    //    afterTextChanged
    // ==============================
    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)

        if (listTranslations.listTranslations.isEmpty()) {
            animator.apply {
                when (s!!.length to motion.currentState) {
                    1 to start -> start_To_Set_3()
                    0 to set_3 -> set_3_To_Start()
                    1 to set_10 -> set_10_To_Set_11()
                    0 to set_11 -> set_11_To_Set_10()
                    1 to set_2 -> set_2_To_Set_16()
                    0 to set_16 -> set_16_To_Set_2()
                    1 to set_15 -> set_15_To_Set_17()
                    0 to set_17 -> set_17_To_Set_15()
                }
            }
        }
    }
}

