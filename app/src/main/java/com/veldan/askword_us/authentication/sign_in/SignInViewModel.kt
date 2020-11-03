package com.veldan.askword_us.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val fragment: SignInFragment,
) : ViewModel() {

    // Coroutine
    private val scope = viewModelScope

    // Firebase
    private val auth = FirebaseAuth.getInstance()

    // Properties
    private val context = fragment.requireContext()


    //==============================
    //          SignIn
    //==============================
    fun signIn(user: User) {
        val email = user.email
        val password = user.password

        if (Verification.verifyEmailPassword(context, email, password)) {
            scope.launch(Dispatchers.Default) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        "Пользователь вошёл".toast(context)
                        transitionToStart()
                    }
                    .addOnFailureListener {
                        "Пользователь не вошёл".toast(context)
                    }
            }
        }
    }

    //==============================
    //          ForgetPassword
    //==============================
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

    //==============================
    //          TransitionToStart
    //==============================
    private fun transitionToStart() {
        val action =
            SignInFragmentDirections.actionSignInFragmentToStartFragment()
        fragment.findNavController().navigate(action)
    }
}