package com.veldan.askword_us.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater)

        binding.startFragment = this

        return binding.root
    }

    fun transitionToDictionary() {
        val action = StartFragmentDirections.actionStartFragmentToDictionaryFragment()
        findNavController().navigate(action)
    }
}
