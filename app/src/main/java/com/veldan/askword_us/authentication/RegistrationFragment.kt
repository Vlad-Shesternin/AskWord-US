package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.databinding.FragmentRegistrationBinding
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var fireDb: FirebaseDatabase
    private lateinit var users: DatabaseReference

    private lateinit var name: String
    private lateinit var surname: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.registrationFragment = this

        auth = FirebaseAuth.getInstance()
        fireDb = FirebaseDatabase.getInstance()
        users = fireDb.getReference("Users")

        return binding.root
    }

    fun transitionToStart() {
        val action =
            RegistrationFragmentDirections.actionRegistrationFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun getNameSurnameEmailPassword() {
        name = binding.editName.text.toString()
        surname = binding.editSurname.text.toString()
        email = binding.editEmail.text.toString()
        password = binding.editPassword.text.toString()
    }

    fun registration() {
        getNameSurnameEmailPassword()
        if (Verification.verifyNameSurname(requireContext(), name, surname) &&
            Verification.verifyEmailPassword(requireContext(), email, password)
        ) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val user = User(email, password)
                    users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user)
                        .addOnSuccessListener {
                            "Пользователя зарегистрировано".toast(requireContext())
                            transitionToStart()
                        }
                }
                .addOnFailureListener {
                    "Пользователя не зарегистрировано".toast(requireContext())
                }
        }
    }
}