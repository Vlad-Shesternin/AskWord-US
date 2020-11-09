package com.veldan.askword_us.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignInWithGoogleViewModelFactory(
    private val fragment: SignInFragment,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInWithGoogleViewModel::class.java)) {
            return SignInWithGoogleViewModel(fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}