package com.veldan.askword_us.database

import android.content.Context
import androidx.room.*

@Database(entities = [WordModel::class], version = 3, exportSchema = false)
@TypeConverters(ConverterListString::class)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDatabaseDao: WordDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database"
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