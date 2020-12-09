package com.veldan.askword_us.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import java.lang.IllegalArgumentException

class DictionaryAnimatorViewModelFactory(
    private val layoutDictionary: FragmentDictionaryBinding,
    private val fragment: DictionaryFragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryAnimatorViewModel::class.java))
            return DictionaryAnimatorViewModel(layoutDictionary, fragment) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}