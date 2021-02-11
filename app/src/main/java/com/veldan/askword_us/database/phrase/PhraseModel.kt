package com.veldan.askword_us.database.phrase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase_table")
data class PhraseModel(
    @PrimaryKey(autoGenerate = true)
    val phraseId: Long = 0L,

    @ColumnInfo(name = "phrase")
    val phrase: String,

    @ColumnInfo(name = "translation")
    val translation: String,
)