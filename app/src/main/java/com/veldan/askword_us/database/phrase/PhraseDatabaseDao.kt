package com.veldan.askword_us.database.phrase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhraseDatabaseDao {

    @Insert
    suspend fun insert(phrase: PhraseModel)

    @Delete
    suspend fun delete(phrase: PhraseModel)

    @Query("SELECT * FROM phrase_table")
    fun getAllPhrase(): LiveData<List<PhraseModel>>?
}