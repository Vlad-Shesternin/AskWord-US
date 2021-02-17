package com.veldan.askword_us.database.selected_phrase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SelectedPhraseDatabaseDao {

    @Insert
    suspend fun insert(phrase: SelectedPhraseModel)

    @Delete
    suspend fun delete(phrase: SelectedPhraseModel)

    @Query("SELECT * FROM selected_phrase_table")
    fun getAllSelectedPhrase(): LiveData<MutableList<SelectedPhraseModel>>?
}