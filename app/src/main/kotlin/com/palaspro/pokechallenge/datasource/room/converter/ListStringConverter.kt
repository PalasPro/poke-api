package com.palaspro.pokechallenge.datasource.room.converter

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun listStringToString(list: List<String>): String {
        var string = ""
        list.forEach { s -> string += "$s|" }
        return string
    }

    @TypeConverter
    fun stringToListString(string: String): List<String> {
        val list = ArrayList<String>()

        string.split("|").map {
            list.add(it)
        }

        return list
    }
}