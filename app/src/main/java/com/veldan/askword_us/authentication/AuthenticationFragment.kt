package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding
import com.veldan.askword_us.global.objects.Verification


class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAuthenticationBinding.inflate(inflater)
        binding.authenticationFragment = this

        return binding.root
    }

    fun transitionToRegistration() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }


//    fun signIn() {
//        email = binding.editEmail.text.toString()
//        password = binding.editPassword.text.toString()
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnSuccessListener {
//                Toast.makeText(context, "Пользователь вошёл", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(context, "Пользователь не вошёл", Toast.LENGTH_SHORT).show()
//            }
//    }
}