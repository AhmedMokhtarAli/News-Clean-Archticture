package com.example.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val articleSource: ArticleSource?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
) : Parcelable