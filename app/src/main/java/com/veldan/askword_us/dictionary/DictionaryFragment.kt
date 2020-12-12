package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.global.interfaces.TransitionListener

class DictionaryFragment : Fragment() {

    // Components
    lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding(inflater)
        initAnimator()

        return binding.root
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryBinding.inflate(inflater)
    }

    // ==============================
    //    Init Animator
    // ==============================
    private fun initAnimator() {
        DictionaryAnimator(binding, this)
    }
}