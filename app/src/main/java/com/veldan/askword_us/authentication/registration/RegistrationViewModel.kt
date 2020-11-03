package com.veldan.askword_us.authentication.registration

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.*

private const val TAG = "RegistrationViewModel"

class RegistrationViewModel : ViewModel() {

    // Coroutine
    private val scope = CoroutineScope(Dispatchers.Default)

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val fireDb = FirebaseDatabase.getInstance()
    private val users = fireDb.getReference("Users")
    private var fireUser: FirebaseUser? = null

    fun registration(fragment: Fragment, user: User) {
        val name = user.name
        val surname = user.surname
        val email = user.email
        val password = user.password

        if (Verification.verifyNameSurname(fragment.requireContext(), name, surname) &&
            Verification.verifyEmailPassword(fragment.requireContext(), email, password)
        ) {
            "Проверка пройдена".toast(fragment.requireContext())

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    "Пользователя зарегистрировано".toast(fragment.requireContext())

                    fireUser = auth.currentUser
                    fireUser!!.sendEmailVerification()
                        .addOnSuccessListener {
                            "Подтвердите адрес: $email".toast(fragment.requireContext())

                            // Checking email confirmation every one second
                            scope.launch {
                                verificationEmail()
                                val userWithoutPassword = User(name, surname, email)
                                addUserFireDb(fragment.requireContext(), userWithoutPassword)
                                transitionToStart(fragment)
                            }
                        }
                        .addOnFailureListener {
                            "Не удалось отправить: $email".toast(fragment.requireContext())
                        }
                }
                .addOnFailureListener {
                    "Пользователя не зарегистрировано".toast(fragment.requireContext())
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

    private suspend fun addUserFireDb(context: Context, user: User) {
        val addUserJob = scope.launch {
            users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user)
                .addOnSuccessListener {
                    "Пользователя добавлено в БД".toast(context)
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

    private fun transitionToStart(fragment: Fragment) {
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToStartFragment()
        fragment.findNavController().navigate(action)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onDetach:")

        scope.cancel()
        Log.i(TAG, "Scope отменено")

        deleteUser()
    }

}