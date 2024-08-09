package com.sa.organicStore.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toArrayList(value: String): ArrayList<Int> {
        val typeList = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, typeList)
    }

    @TypeConverter
    fun fromArrayList(value: ArrayList<Int>): String {
        val typeList = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().toJson(value, typeList)
    }
}