package edu.cc231030.MC.project.data.db

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

// Converter to convert a List to a String that it can be stored in the database
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromListToString(exerciseIds: List<Int>): String {
        return gson.toJson(exerciseIds)
    }

    @TypeConverter
    fun fromStringToList(data: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }
}
