package com.veldan.askword_us.dictionary.word_creator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.veldan.askword_us.database.WordDatabaseDao
import com.veldan.askword_us.database.WordModel
import kotlinx.coroutines.launch

class WordCreatorViewModel(
    private val databaseDao: WordDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val words = databaseDao.getAllWords()

    fun insert(word: WordModel) {
        viewModelScope.launch {
            databaseDao.insert(word)
        }
    }
}