package com.veldan.askword_us.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDatabaseDao {

    @Insert
    fun insert(word: WordModel)

    @Query("SELECT * FROM word_table")
    fun getAllWords(): LiveData<List<WordModel>>?

    @Query("DELETE FROM word_table")
    fun clear()
}