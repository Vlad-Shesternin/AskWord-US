package com.veldan.askword_us.dictionary.word_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryFragment

class WordCreatorAnimatorViewModelFactory(
    private val layoutWordCreation: LayoutWordCreatorBinding,
    private val fragment: DictionaryFragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordCreatorAnimatorViewModel::class.java))
            return WordCreatorAnimatorViewModel(layoutWordCreation, fragment) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}