package com.veldan.askword_us.dictionary

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.word.WordDatabaseDao

class DictionaryViewModelFactory(
    private val wordDatabaseDao: WordDatabaseDao,
    private val phraseDatabaseDao: PhraseDatabaseDao,
    private val application: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java))
            return DictionaryViewModel(wordDatabaseDao,phraseDatabaseDao, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
