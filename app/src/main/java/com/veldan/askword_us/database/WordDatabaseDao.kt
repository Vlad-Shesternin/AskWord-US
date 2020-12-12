package com.veldan.askword_us.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDatabaseDao {

    @Insert
    suspend fun insert(word: WordModel)

    @Query("DELETE FROM word_table")
    suspend fun clear()

    @Query("SELECT * FROM word_table")
    fun getAllWords(): LiveData<List<WordModel>>?
}