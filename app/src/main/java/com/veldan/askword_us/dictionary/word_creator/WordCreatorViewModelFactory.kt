package com.veldan.askword_us.dictionary.word_creator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.database.word.WordDatabaseDao
import java.lang.IllegalArgumentException

class WordCreatorViewModelFactory(
    private val databaseDao: WordDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordCreatorViewModel::class.java))
            return WordCreatorViewModel(databaseDao, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}