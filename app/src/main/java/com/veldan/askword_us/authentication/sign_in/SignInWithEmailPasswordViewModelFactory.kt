package com.veldan.askword_us.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignInWithEmailPasswordViewModelFactory(
    private val fragment: SignInFragment,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInWithEmailPasswordViewModel::class.java)) {
            return SignInWithEmailPasswordViewModel(fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}