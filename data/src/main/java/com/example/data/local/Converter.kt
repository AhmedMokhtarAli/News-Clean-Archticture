package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source?):String?{
        return source?.name
    }

    @TypeConverter
    fun toSource(name:String?):Source{
        return Source(name, name)
    }
}