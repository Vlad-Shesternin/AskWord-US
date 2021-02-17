package com.veldan.askword_us.studying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentStudyFormatBinding
import com.veldan.askword_us.databinding.FragmentStudyingBinding
import com.veldan.askword_us.global.objects.Animator2


class StudyingFragment :
    Fragment(),
    View.OnClickListener {

    // Binding
    private lateinit var binding: FragmentStudyingBinding

    // ComponentsUI
    private lateinit var fabSettings: ImageButton
    private lateinit var fabNext: ImageButton

    // Components
    private val animations = StudyingAnimations
    private val animator = Animator2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        binding = FragmentStudyingBinding.inflate(inflater)
    }

    // ==============================
    //    init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            fabSettings = it.fabSettings
            fabNext = it.fabNext
        }
    }

    // ==============================
    //    init Listeners
    // ==============================
    private fun initListeners() {
        fabSettings.setOnClickListener(this)
        fabNext.setOnClickListener(this)
    }

    // ==============================
    //    init Components
    // ==============================
    private fun initComponents() {
        initAnimator()
    }

    // ==============================
    //    init Animator
    // ==============================
    private fun initAnimator() {
        animator.motion = binding.root
    }

    // ==============================
    //    transitionToStudy
    // ==============================
    private fun transitionToStudy() {
        findNavController().popBackStack()
    }


    // ==============================
    //    on Click
    // ==============================
    override fun onClick(v: View) {
        animator.apply {
            animations.apply {
                when (v.id) {
                    fabSettings.id -> {
                        transition(start to show_settings)
                        // transitionToStudy()
                    }
                    fabNext.id -> {
                        transition(start to next_word)
                    }
                }
            }
        }
    }
}