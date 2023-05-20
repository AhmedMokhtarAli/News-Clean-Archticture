package com.example.domain.repository

import com.example.domain.entity.Article
import com.example.domain.entity.NewsResponse
import com.example.domain.utilis.NewsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsRepository {
    suspend fun getNewsFromRemote(country:String, pageNumber:Int):NewsResource<NewsResponse>
    suspend fun searchForNews(searchQuery:String,pageNumber: Int):NewsResource<NewsResponse>
    fun getNewsFromLocal(): Flow<List<Article>>
    suspend fun addNewToFavorit(article: Article)
    suspend fun removeFromFavorit(article: Article)

}