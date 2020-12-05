package com.veldan.askword_us.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordModel(
    @PrimaryKey(autoGenerate = true)
    val wordId: Long = 0L,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "translation")
    val translation: String,

    @ColumnInfo(name = "prompt")
    val prompt: String,
)