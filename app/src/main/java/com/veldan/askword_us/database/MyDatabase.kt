package com.veldan.askword_us.database

import android.content.Context
import androidx.room.*
import com.veldan.askword_us.database.phrase.PhraseDatabaseDao
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseDatabaseDao
import com.veldan.askword_us.database.selected_phrase.SelectedPhraseModel
import com.veldan.askword_us.database.selected_word.SelectedWordDatabaseDao
import com.veldan.askword_us.database.selected_word.SelectedWordModel
import com.veldan.askword_us.database.word.ConverterListString
import com.veldan.askword_us.database.word.WordDatabaseDao
import com.veldan.askword_us.database.word.WordModel

@Database(
    entities = [
        WordModel::class,
        PhraseModel::class,
        SelectedWordModel::class,
        SelectedPhraseModel::class
    ],
    version = 5,
    exportSchema = false)
@TypeConverters(ConverterListString::class)
abstract class MyDatabase : RoomDatabase() {
    abstract val wordDatabaseDao: WordDatabaseDao
    abstract val phraseDatabaseDao: PhraseDatabaseDao
    abstract val selectedWordDatabaseDao: SelectedWordDatabaseDao
    abstract val selectedPhraseDatabaseDao: SelectedPhraseDatabaseDao
    abstract val databaseDao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}