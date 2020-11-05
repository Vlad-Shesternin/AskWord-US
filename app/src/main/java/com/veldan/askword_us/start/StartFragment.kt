package com.veldan.askword_us.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private val TAG = "StartFragment"

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStartBinding.inflate(inflater)

        binding.startFragment = this

        return binding.root
    }

    fun transitionToDictionary() {
        val action = StartFragmentDirections.actionStartFragmentToDictionaryFragment()
        findNavController().navigate(action)
    }

    fun clickOnAccount() {
        val args = StartFragmentArgs.fromBundle(requireArguments())
        binding.layoutAccount.also {
            it.tvNameSurname.text = args.userName + " " + args.userSurname
            it.layoutAccount.visibility = View.VISIBLE
        }
    }
}
