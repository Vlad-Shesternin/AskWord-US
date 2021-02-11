package com.veldan.askword_us.dictionary

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.word_creator.WordCreatorAnimator
import com.veldan.askword_us.dictionary.word_creator.WordCreatorDialog
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.general_classes.Camera
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.RequestCode
import com.veldan.askword_us.global.toast
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.tabs.view.*
import java.io.IOException

class DictionaryFragment :
    Fragment(),
    View.OnClickListener,
    View.OnLongClickListener,
    TransitionListener {

    // Binding
    private lateinit var binding: FragmentDictionaryBinding

    // ViewModel
    private lateinit var viewModel: DictionaryViewModel

    // Components
    private val TAG = this::class.simpleName
    private val animator = DictionaryAnimator
    private val animatorWordCreator = WordCreatorAnimator
    private lateinit var adapterWord: WordAdapter
    private lateinit var adapterPhrase: PhraseAdapter
    private lateinit var wordCreator: WordCreatorDialog

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var tabWord: Button
    private lateinit var tabPhrase: Button
    private lateinit var fabAdd: ImageButton
    private lateinit var fabFile: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var fabPhoto: ImageButton
    private lateinit var fabCategory: ImageButton
    private lateinit var fabAddPhrase: ImageButton
    private lateinit var fabBackPhrase: ImageButton
    private lateinit var layoutWordCreator: LayoutWordCreatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initViewModel()
        initComponentsUI()
        initComponents()
        initListeners()

        return binding.root
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryBinding.inflate(inflater)
    }

    // ==============================
    //    Init ViewModel
    // ==============================
    private fun initViewModel() {
        val application = requireNotNull(activity).application
        val wordDatabaseDao = MyDatabase.getInstance(application).wordDatabaseDao
        val phraseDatabaseDao = MyDatabase.getInstance(application).phraseDatabaseDao

        val viewModelFactory =
            DictionaryViewModelFactory(wordDatabaseDao, phraseDatabaseDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DictionaryViewModel::class.java)
    }

    // ==============================
    //    Init ComponentsUI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motion = it.motionDictionary
            tabWord = it.tabs.btnWords
            tabPhrase = it.tabs.btnPhrases
            fabAdd = it.fabAdd
            fabBack = it.fabBack
            fabFile = it.fabFile
            fabPhoto = it.fabPhoto
            fabCategory = it.fabCategory
            fabAddPhrase = it.layoutPhraseCreator.fabAdd
            fabBackPhrase = it.layoutPhraseCreator.fabBackPhrase
            layoutWordCreator = it.layoutWordCreator
        }
    }

    // ==============================
    //    Init Components
    // ==============================
    private fun initComponents() {
        binding.tabs.btnWords.background = resources.getDrawable(R.drawable.tab_press)
        DictionaryAnimator.motion = this.motion

        initWordAdapter()
        initPhraseAdapter()
    }

    private fun initWordAdapter() {
        adapterWord = WordAdapter(viewModel, animator, binding)
        binding.rvListWords.adapter = adapterWord

        viewModel.words?.observe(viewLifecycleOwner, Observer {
            adapterWord.words = it
            Log.i(TAG, "value = $it")
        })
    }

    private fun initPhraseAdapter() {
        adapterPhrase = PhraseAdapter(viewModel, animator, binding)
        binding.layoutPhrases.rvListPhrases.adapter = adapterPhrase

        viewModel.phrases?.observe(viewLifecycleOwner, Observer {
            adapterPhrase.phrases = it
            Log.i(TAG, "value = $it")
        })
    }

    // ==============================
    //    Init Listeners
    // ==============================
    private fun initListeners() {
        binding.also {
            // onClick
            fabAdd.setOnClickListener(this)
            fabFile.setOnClickListener(this)
            fabBack.setOnClickListener(this)
            fabPhoto.setOnClickListener(this)
            fabCategory.setOnClickListener(this)
            tabWord.setOnClickListener(this)
            tabPhrase.setOnClickListener(this)
            fabAddPhrase.setOnClickListener(this)
            fabBackPhrase.setOnClickListener(this)
            // onLongClick
            fabAdd.setOnLongClickListener(this)
            // onTransition
            motion.setTransitionListener(this)
        }
    }

    // ==============================
    //    Init WordCreator
    // ==============================
    private fun initWordCreatorDialog() {
        wordCreator = WordCreatorDialog(this, layoutWordCreator)
    }

    // ==============================
    //    Transition to DictionaryOrStudy
    // ==============================
    private fun transitionToDictionaryOrStudy() {
        findNavController().popBackStack()
    }

    private fun getPhraseModel(): PhraseModel? {
        val phrase = binding.layoutPhraseCreator.editPhrase.text.toString()
        val translate = binding.layoutPhraseCreator.editTranslation.text.toString()
        return if (phrase != "" && translate != "")
            PhraseModel(
                phrase = phrase,
                translation = translate
            ) else null
    }

    private fun insert() {
        val phrase = getPhraseModel()
        phrase?.let {
            viewModel.insertPhrase(phrase)
        }
        clearAll()
    }

    private fun clearAll() {
        binding.layoutPhraseCreator.apply {
            editPhrase.text.clear()
            editTranslation.text.clear()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == RequestCode.RC_CAMERA) {
            val camera = Camera(this)
            if (camera.cameraPermissionGranted()) {
                camera.startCamera(layoutWordCreator.preview)
                layoutWordCreator.preview.also {
                    it.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode.RC_PICK_IMAGE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            wordCreator.savedUri = imageUri
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                layoutWordCreator.apply {
                    ivCapture.setImageBitmap(bitmap)

                    animatorWordCreator.apply {
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
                            when (WordCreatorAnimator.trigger) {
                                WordCreatorAnimator.set_7 -> WordCreatorAnimator.set_9_To_Set_10()
                                WordCreatorAnimator.set_8 -> WordCreatorAnimator.set_9_To_Set_11()
                            }
                        }
                    }

                    ivCancelCapture.setOnClickListener {
                        Camera(this@DictionaryFragment).deleteImage(imageUri!!.path!!)
                        ivCapture.setImageResource(0)
                        animator.apply {
                            when (WordCreatorAnimator.trigger) {
                                WordCreatorAnimator.set_7 -> WordCreatorAnimator.set_9_To_Set_7()
                                WordCreatorAnimator.set_8 -> WordCreatorAnimator.set_9_To_Set_8()
                            }
                        }
                    }
                }


            } catch (e: IOException) {
                "Error: $e".toast(requireContext())
            }
        }
    }

    // ==============================
    //    onClick
    // ==============================
    override fun onClick(view: View) {
        when (view.id) {
            tabWord.id -> {
                tabWord.background = resources.getDrawable(R.drawable.tab_press)
                tabPhrase.background = resources.getDrawable(R.drawable.tab_enable)
                animator.set_8_To_Start()
            }
            tabPhrase.id -> {
                tabPhrase.background = resources.getDrawable(R.drawable.tab_press)
                tabWord.background = resources.getDrawable(R.drawable.tab_enable)
                animator.start_To_Set_8()
            }
            fabBackPhrase.id -> {
                animator.set_9_To_Set_8()
            }
            fabAddPhrase.id -> {
                insert()
            }
        }
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (view.id to motion.currentState) {
            fabAdd.id to animator.start -> {
                initWordCreatorDialog()
                animator.start_To_Set_6()
            }
            fabAdd.id to animator.set_8 -> {
                animator.set_8_To_Set_9()
                binding.layoutPhraseCreator.editPhrase.defaultFocusAndKeyboard(true)
            }
            fabAdd.id to animator.set_2 -> {
                animator.set_2_To_Set_1()
            }
            fabCategory.id to animator.set_2 -> {
                animator.set_2_To_Set_3()
            }
            fabPhoto.id to animator.set_2 -> {
                animator.set_2_To_Set_4()
            }
            fabFile.id to animator.set_2 -> {
                animator.set_2_To_Set_5()
            }
            fabBack.id to animator.start -> {
                transitionToDictionaryOrStudy()
            }
            fabBack.id to animator.set_2 -> {
                transitionToDictionaryOrStudy()
            }
            fabBack.id to animator.set_6 -> {
                animator.set_6_To_Start()
            }
            fabBack.id to animator.set_7 -> {
                animator.set_7_To_Start()
            }
            fabBack.id to animator.set_8 -> {
                transitionToDictionaryOrStudy()
            }
            fabBack.id to animator.set_10 -> {
                animator.set_10_To_Set_8()
            }
        }
    }

    // ==============================
    //    onLongClick
    // ==============================
    override fun onLongClick(view: View): Boolean {
        when (view.id to motion.currentState) {
            fabAdd.id to animator.start -> animator.start_To_Set_1()
        }
        return true
    }

    // ==============================
    //    onTransitionCompleted
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {
        when (motionLayout!!.startState to end) {
            animator.start to animator.set_1 -> animator.set_1_To_Set_2()
            animator.set_2 to animator.set_1 -> animator.set_1_To_Start()
        }
    }
}