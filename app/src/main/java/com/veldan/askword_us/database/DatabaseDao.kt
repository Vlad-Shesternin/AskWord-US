package com.veldan.askword_us.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DatabaseDao {

    @Query("DELETE FROM word_table")
    suspend fun wordsDelete()

    @Query("DELETE FROM phrase_table")
    suspend fun phrasesDelete()

    @Query("DELETE FROM selected_word_table")
    suspend fun selectedWordsDelete()

    @Query("DELETE FROM selected_phrase_table")
    suspend fun selectedPhrasesDelete()
}