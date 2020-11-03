package com.veldan.askword_us.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RegistrationViewModelFactory(
    private val fragment: RegistrationFragment,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}