package com.veldan.askword_us.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignInViewModelFactory(
    private val fragment: SignInFragment,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}