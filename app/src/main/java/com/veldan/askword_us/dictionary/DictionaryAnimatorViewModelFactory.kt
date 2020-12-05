package com.veldan.askword_us.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.FragmentDictionaryStartBinding
import java.lang.IllegalArgumentException

class DictionaryAnimatorViewModelFactory(
    private val layoutDictionary: FragmentDictionaryStartBinding,
    private val fragment: DictionaryFragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryAnimatorViewModel::class.java))
            return DictionaryAnimatorViewModel(layoutDictionary, fragment) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}