package com.veldan.askword_us.dictionary.word_creator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.database.word.WordModel
import kotlinx.coroutines.launch

class WordCreatorViewModel(
    private val databaseDao: WordDatabaseDao,
    application: Application,
) : AndroidViewModel(application) {

    fun insert(word: WordModel) {
        viewModelScope.launch {
            Log.i("WordCreatorDialog", "launch: ${word.translations}")
            databaseDao.insert(word)
        }
    }
}