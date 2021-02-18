package com.veldan.askword_us.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.veldan.askword_us.databinding.FragmentStartBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.AUTH

class StartFragment : Fragment() {
    val TAG = "StartFragment"

    // Binding
    private lateinit var binding: FragmentStartBinding

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private var user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        transitionToDictionaryAndStudyOrAuthentication()

        return binding.root
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentStartBinding.inflate(inflater)
    }

    // ==============================
    //    DictionaryOrStudy Or Authentication
    // ==============================
    private fun transitionToDictionaryAndStudyOrAuthentication() {
        if (currentUser() != null) {
            if (currentUser()!!.isEmailVerified) {
                val sharedPref = SharedPreferences(this).initSharedPref(AUTH)
                val name = sharedPref.getString(SharedPreferences.USER_NAME, "name")!!
                val surname = sharedPref.getString(SharedPreferences.USER_SURNAME, "surname")!!
                val email = user!!.email!!
                transitionToDictionaryOrStudy(name, surname, email)
            } else {
                currentUser()!!.delete().addOnSuccessListener {
                    Log.i(TAG, "User was no Verificated DELETED")
                }
                transitionToAuthentication()
            }
        } else {
            transitionToAuthentication()
        }
    }

    // ==============================
    //    CurrentUser
    // ==============================
    private fun currentUser(): FirebaseUser? {
        user = auth.currentUser
        return user
    }

    // ==============================
    //    to Authentication
    // ==============================
    private fun transitionToAuthentication() {
        val action = StartFragmentDirections.actionStartFragmentToAuthentication()
        findNavController().navigate(action)
    }

    // ==============================
    //    to DictionaryOrStudy
    // ==============================
    private fun transitionToDictionaryOrStudy(name: String, surname: String, email: String) {
        val action = StartFragmentDirections.actionStartFragmentToDictionaryOrStudy(
            name,
            surname,
            email
        )
        findNavController().navigate(action)
    }
}