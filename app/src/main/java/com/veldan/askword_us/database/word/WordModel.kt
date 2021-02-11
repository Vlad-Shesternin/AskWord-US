package com.veldan.askword_us.database.word

import androidx.room.*

@Entity(tableName = "word_table")
data class WordModel(
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