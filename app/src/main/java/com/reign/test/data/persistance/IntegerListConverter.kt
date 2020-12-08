package com.reign.test.data.persistance

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reign.test.data.models.Hit


class IntegerListConverter {

    @TypeConverter
    fun fromString(value: String): List<Hit>? {
        val listType = object : TypeToken<List<Hit>>() {}.type
        return Gson().fromJson<List<Hit>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Hit>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
