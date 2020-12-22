package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.dictionary.word_creator.WordCreatorDialog
import com.veldan.askword_us.dictionary_or_study.DictionaryOrStudyFragmentDirections
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator

class DictionaryFragment :
    Fragment(),
    View.OnClickListener,
    View.OnLongClickListener,
    TransitionListener {

    // Binding
    private lateinit var binding: FragmentDictionaryBinding

    // Components
    private val animator = DictionaryAnimator

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var fabAdd: ImageButton
    private lateinit var fabFile: ImageButton
    private lateinit var fabBack: ImageButton
    private lateinit var fabPhoto: ImageButton
    private lateinit var fabCategory: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
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
    //    Init ComponentsUI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motion = it.motionDictionary
            fabAdd = it.fabAdd
            fabBack = it.fabBack
            fabFile = it.fabFile
            fabPhoto = it.fabPhoto
            fabCategory = it.fabCategory
        }
    }

    // ==============================
    //    Init Components
    // ==============================
    private fun initComponents() {
        DictionaryAnimator.motion = this.motion
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
        WordCreatorDialog(this, binding.layoutWordCreator)
    }

    // ==============================
    //    Transition to DictionaryOrStudy
    // ==============================
    private fun transitionToDictionaryOrStudy() {
        findNavController().popBackStack()
    }

    // ==============================
    //    onClick
    // ==============================
    override fun onClick(view: View) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (view.id to motion.currentState) {
            fabAdd.id to animator.start -> {
                initWordCreatorDialog()
                animator.start_To_Set_6()
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