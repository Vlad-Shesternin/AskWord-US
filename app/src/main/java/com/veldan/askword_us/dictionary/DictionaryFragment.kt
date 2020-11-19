package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.dictionary.animators.DictionaryAnimator

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding(inflater)
        initAnimator()
        initAdapter()

        return binding.root
    }

    // ==============================
    //        Initializing
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryBinding.inflate(inflater)
    }

    private fun initAdapter() {
        // val adapter = WordAdapter()
        // binding.rvListWords.adapter = adapter
    }

    private fun initAnimator() {
        DictionaryAnimator(binding)
    }
}