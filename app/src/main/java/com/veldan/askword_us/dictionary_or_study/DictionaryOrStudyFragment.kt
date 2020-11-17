package com.veldan.askword_us.dictionary_or_study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.veldan.askword_us.databinding.FragmentDictionaryOrStudyBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences

class DictionaryOrStudyFragment : Fragment() {
    private val TAG = "DictionaryOrStudyFragment"

    // Binding
    private lateinit var binding: FragmentDictionaryOrStudyBinding

    // Firebase
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding(inflater)

        return binding.root
    }

    // ==============================
    //        Initializing
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryOrStudyBinding.inflate(inflater)
        binding.dictionaryOrStudyFragment = this
        binding.layoutAccount.dictionaryOrStudyFragment = this
    }

    // ==============================
    //        Transition
    // ==============================
    fun transitionToDictionary() {
        val action =
            DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToDictionaryFragment()
        findNavController().navigate(action)
    }

    fun transitionToAuthentication() {
        auth.signOut()

        val action =
            DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToAuthenticationFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //          ClickOnAccount
    // ==============================
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
