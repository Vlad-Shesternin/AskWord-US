package com.veldan.askword_us.database.selected_phrase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_phrase_table")
data class SelectedPhraseModel(
    @PrimaryKey(autoGenerate = true)
    val phraseId: Long = 0L,

    @ColumnInfo(name = "phrase")
    val phrase: String,

    @ColumnInfo(name = "translation")
    val translation: String,
)