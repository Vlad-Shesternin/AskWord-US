package com.veldan.askword_us.database.selected_word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SelectedWordDatabaseDao {

    @Insert
    suspend fun insert(word: SelectedWordModel)

    @Delete
    suspend fun delete(word: SelectedWordModel)

    @Query("SELECT * FROM selected_word_table")
    fun getAllSelectedWords(): LiveData<MutableList<SelectedWordModel>>?
}