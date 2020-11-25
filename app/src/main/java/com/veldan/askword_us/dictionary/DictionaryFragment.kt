package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veldan.askword_us.databinding.FragmentDictionaryStartBinding
import com.veldan.askword_us.dictionary.animators.DictionaryAnimator

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryStartBinding

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
    //        Initializing
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryStartBinding.inflate(inflater)
    }

    private fun initAnimator() {
        DictionaryAnimator(binding, requireContext())
    }
}