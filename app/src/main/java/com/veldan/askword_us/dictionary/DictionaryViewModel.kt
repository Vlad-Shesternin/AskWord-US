package com.veldan.askword_us.dictionary

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.database.word.WordModel
import kotlinx.coroutines.launch

class DictionaryViewModel(
    private val wordDatabaseDao: WordDatabaseDao,
    private val phraseDatabaseDao: PhraseDatabaseDao,
    application: Application,
) : AndroidViewModel(application) {

    val words = wordDatabaseDao.getAllWords()
    val phrases = phraseDatabaseDao.getAllPhrase()

    fun insertPhrase(phrase: PhraseModel) {
        viewModelScope.launch {
            phraseDatabaseDao.insert(phrase)
        }
    }

    fun wordDelete(word: WordModel) {
        viewModelScope.launch {
            wordDatabaseDao.delete(word)
        }
    }

    fun phraseDelete(phrase: PhraseModel) {
        viewModelScope.launch {
            phraseDatabaseDao.delete(phrase)
        }
    }
}