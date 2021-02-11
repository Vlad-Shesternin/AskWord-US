package com.veldan.askword_us.study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {

    private lateinit var binding: FragmentStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)

        return binding.root
    }

    // ==============================
    //    initBinding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentStudyBinding.inflate(inflater)
    }
}