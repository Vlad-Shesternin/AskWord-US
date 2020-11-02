package com.veldan.askword_us.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.databinding.FragmentRegistrationBinding
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.*

private const val TAG = "RegistrationFragment"

class RegistrationFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentRegistrationBinding

    // Coroutine
    private val scope = CoroutineScope(Dispatchers.Default)

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val fireDb = FirebaseDatabase.getInstance()
    private val users = fireDb.getReference("Users")
    private var fireUser: FirebaseUser? = null

    // Properties
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

        return binding.root
    }

    private fun transitionToStart() {
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
            "Проверка пройдена".toast(requireContext())

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    "Пользователя зарегистрировано".toast(requireContext())

                    fireUser = auth.currentUser
                    fireUser!!.sendEmailVerification()
                        .addOnSuccessListener {
                            "Подтвердите адрес: $email".toast(requireContext())

                            // Checking email confirmation every one second
                            scope.launch {
                                verificationEmail()
                                addUserFireDb()
                                transitionToStart()
                            }

                        }
                        .addOnFailureListener {
                            "Не удалось отправить: $email".toast(requireContext())
                        }
                }
                .addOnFailureListener {
                    "Пользователя не зарегистрировано".toast(requireContext())
                }
        }
    }

    private suspend fun verificationEmail() {
        val verifyJob = scope.launch {
            var a = 0
            fireUser?.let { user ->
                while (!(user.isEmailVerified)) {
                    delay(1000)
                    user.reload()
                    Log.i(TAG, "Подтвердите...${++a}")
                }
            }
        }
        verifyJob.join()
    }

    private suspend fun addUserFireDb() {
        val addUserJob = scope.launch {
            val user = User(name, surname, email)
            users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user)
                .addOnSuccessListener {
                    "Пользователя добавлено в БД".toast(requireContext())
                }
        }
        addUserJob.join()
    }

    private fun deleteUser() {

        CoroutineScope(Dispatchers.Default).launch {
            fireUser?.let {
                it.delete()
                    .addOnSuccessListener {
                        Log.i(TAG, "Пользователя удалено")
                    }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: ")

        scope.cancel()
        "Scope отменено".toast(requireContext())

        deleteUser()
    }
}