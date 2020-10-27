package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding
import com.veldan.askword_us.global.objects.Verification


class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var fireDb: FirebaseDatabase
    private lateinit var users: DatabaseReference

    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAuthenticationBinding.inflate(inflater)
        binding.authenticationFragment = this

        auth = FirebaseAuth.getInstance()
        fireDb = FirebaseDatabase.getInstance()
        users = fireDb.getReference("Users")

        return binding.root
    }

    private fun getEmailPassword() {
        email = binding.editEmail.text.toString()
        password = binding.editPassword.text.toString()
    }

    fun registration() {
        getEmailPassword()
        if (Verification.verify(requireContext(), email, password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val user = User(email, password)
                    users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(context,
                                "Пользователя зарегистрировано",
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Пользователя не зарегистрировано", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    fun signIn() {
        email = binding.editEmail.text.toString()
        password = binding.editPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(context, "Пользователь вошёл", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Пользователь не вошёл", Toast.LENGTH_SHORT).show()
            }
    }
}