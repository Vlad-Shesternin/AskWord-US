package com.veldan.askword_us.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.databinding.FragmentStartBinding
import com.veldan.askword_us.global.objects.SharedPreferences

class StartFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentStartBinding

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private var user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding(inflater)
        transitionToDictionaryAndStudyOrAuthentication()

        return binding.root
    }

    // ==============================
    //        Initializing
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentStartBinding.inflate(inflater)
    }

    // ==============================
    // DictionaryAndStudy Or Authentication
    // ==============================
    private fun transitionToDictionaryAndStudyOrAuthentication() {
        if (currentUser() != null) {
            val sharedPref = SharedPreferences(this).initSharedPref()
            val name = sharedPref.getString(SharedPreferences.USER_NAME, "name")!!
            val surname = sharedPref.getString(SharedPreferences.USER_SURNAME, "surname")!!
            val email = user!!.email!!
            transitionToDictionaryOrStudy(name, surname, email)
        } else {
            transitionToAuthentication()
        }
    }

    // ==============================
    //        CurrentUser
    // ==============================
    private fun currentUser(): FirebaseUser? {
        user = auth.currentUser
        return user
    }

    // ==============================
    //        Transitions
    // ==============================
    private fun transitionToAuthentication() {
        val action = StartFragmentDirections.actionStartFragmentToAuthenticationFragment()
        findNavController().navigate(action)
    }

    private fun transitionToDictionaryOrStudy(name: String, surname: String, email: String) {
        val action = StartFragmentDirections.actionStartFragmentToDictionaryOrStudyFragment(name,
            surname,
            email)
        findNavController().navigate(action)
    }
}