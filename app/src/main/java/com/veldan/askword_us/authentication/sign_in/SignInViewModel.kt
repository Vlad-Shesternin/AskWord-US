package com.veldan.askword_us.authentication.sign_in

import android.text.method.TextKeyListener.clear
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.global.objects.SharedPreferences
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val fragment: SignInFragment,
) : ViewModel() {
    private val TAG = "SignInFragment"

    // Coroutine
    private val scope = viewModelScope

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val fireDb = FirebaseDatabase.getInstance()
    private val users = fireDb.getReference("Users")

    // Properties
    private val context = fragment.requireContext()

    // ==============================
    //           SignIn
    // ==============================
    fun signIn(user: User) {
        val email = user.email
        val password = user.password

        if (Verification.verifyEmailPassword(context, email, password)) {
            scope.launch(Dispatchers.Default) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        getUserForAccount(email)
                        "Пользователь вошёл".toast(context)
                    }

                    .addOnFailureListener {
                        "Пользователь не вошёл".toast(context)
                    }
            }
        }
    }

    // ==============================
    //           ForgetPassword
    // ==============================
    fun forgetPassword(user: User) {
        val email = user.email

        if (Verification.verifyEmail(context, email)) {
            scope.launch(Dispatchers.Default) {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        "Инструкцию по восстановлению пароля отправлено на почту: $email".toast(
                            context)
                    }
                    .addOnFailureListener {
                        "Нет такой почты".toast(context)
                    }
            }
        }
    }

    // ==============================
    //       GetUserForAccount
    // ==============================
    private fun getUserForAccount(email: String) {
        users.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val user = data.getValue(User::class.java)!!
                        user.also {
                            "Success getUserForAccount".toast(context)

                            val editor = SharedPreferences(fragment).getEditor()
                            editor.putString(SharedPreferences.USER_NAME, it.name)
                            editor.putString(SharedPreferences.USER_SURNAME, it.surname)
                            editor.apply()

                            transitionToDictionaryOrStudy(it.name, it.surname, it.email)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    "Fail getUserForAccount".toast(context)
                }
            })
    }

    // ==============================
    //       TransitionToStart
    // ==============================
    private fun transitionToDictionaryOrStudy(name: String, surname: String, email: String) {
        val action =
            SignInFragmentDirections.actionSignInFragmentToDictionaryOrStudyFragment(name,
                surname,
                email)
        fragment.findNavController().navigate(action)
    }
}