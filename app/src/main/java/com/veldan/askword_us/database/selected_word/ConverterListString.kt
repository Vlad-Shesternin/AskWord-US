package com.veldan.askword_us.database.selected_word

import android.util.Log
import androidx.room.TypeConverter

class ConverterListString {
    @TypeConverter
    fun stringToList(string: String): List<String> {
        val list = string.split(",").map { it }
        Log.i("WordCreatorDialog", "string - list: $list")
        return list
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        val string = list.joinToString(separator = ",")
        Log.i("WordCreatorDialog", "list - string: $string")
        return string
    }
}