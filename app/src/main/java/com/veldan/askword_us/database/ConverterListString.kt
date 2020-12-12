package com.veldan.askword_us.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterListString {
    @TypeConverter
    fun listToString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun stringToList(json: String): List<String> {
        val listStringType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, listStringType)
    }
}