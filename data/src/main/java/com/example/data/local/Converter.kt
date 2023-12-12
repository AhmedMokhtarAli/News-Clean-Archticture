package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.ArticleSource

class Converter {

    @TypeConverter
    fun fromSource(articleSource: ArticleSource?):String?{
        return articleSource?.name
    }

    @TypeConverter
    fun toSource(name:String?):ArticleSource{
        return ArticleSource(name, name)
    }
}