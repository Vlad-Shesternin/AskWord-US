package com.veldan.askword_us.database.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDatabaseDao {

    @Insert
    suspend fun insert(word: WordModel)

    @Delete
    suspend fun delete(word: WordModel)

    @Query("SELECT * FROM word_table")
    fun getAllWords(): LiveData<MutableList<WordModel>>?
}