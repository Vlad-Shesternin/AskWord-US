package com.veldan.askword_us.database.selected_word

import androidx.room.*

@Entity(tableName = "selected_word_table")
data class SelectedWordModel(
    @PrimaryKey(autoGenerate = true)
    val wordId: Long = 0L,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "translations")
    @TypeConverters(ConverterListString::class)
    val translations: List<String>,

    @ColumnInfo(name = "prompt")
    val prompt: String,
)