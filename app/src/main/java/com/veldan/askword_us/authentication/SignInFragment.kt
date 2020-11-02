package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.databinding.FragmentSignInBinding
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    // Firebase
    private lateinit var auth: FirebaseAuth

    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater)
        binding.signInFragment = this

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    fun transitionToStart() {
        val action =
            SignInFragmentDirections.actionSignInFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun getEmailPassword() {
        email = binding.editEmail.text.toString()
        password = binding.editPassword.text.toString()
    }

    fun signIn() {
        getEmailPassword()
        if (Verification.verifyEmailPassword(requireContext(), email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    "Пользователь вошёл".toast(requireContext())
                    transitionToStart()
                }
                .addOnFailureListener {
                    "Пользователь не вошёл".toast(requireContext())
                }
        }
    }

    fun forgetPassword() {
        email = binding.editEmail.text.toString()
        if (Verification.verifyEmail(requireContext(), email)) {
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    "Инструкцию по восстановлению пароля отправлено на почту: $email".toast(
                        requireContext())
                }
                .addOnFailureListener {
                    "Нет такой почты".toast(requireContext())
                }

        }
    }
}