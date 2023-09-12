package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface NewsDao {

    @Query("SELECT * FROM articles")
    fun getFavNews(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(article: Article):Long

    @Delete
    suspend fun removeFromFav(article: Article)

}*/
