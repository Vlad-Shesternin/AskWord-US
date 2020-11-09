package com.veldan.askword_us.dictionary_or_study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.databinding.FragmentDictionaryOrStudyBinding

class DictionaryOrStudyFragment : Fragment() {
    private val TAG = "DictionaryOrStudyFragment"

    private lateinit var binding: FragmentDictionaryOrStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDictionaryOrStudyBinding.inflate(inflater)
        binding.dictionaryOrStudyFragment = this

        return binding.root
    }

    fun transitionToDictionary() {
        val action = DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToDictionaryFragment()
        findNavController().navigate(action)
    }

    private var visibility = View.INVISIBLE
    fun clickOnAccount() {
        if (visibility == View.INVISIBLE) {
            visibility = View.VISIBLE
            val args = DictionaryOrStudyFragmentArgs.fromBundle(requireArguments())
            binding.layoutAccount.also {
                it.tvNameSurname.text = args.userName + " " + args.userSurname
                it.tvEmail.text = args.userEmail
                it.layoutAccount.visibility = visibility
            }
        } else {
            visibility = View.INVISIBLE
            binding.layoutAccount.layoutAccount.visibility = visibility
        }
    }
}
