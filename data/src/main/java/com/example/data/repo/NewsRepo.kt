package com.example.data.repo

import com.example.data.model.Article
import com.example.data.model.BaseEndPointResponse
import com.example.data.model.ArticleSource
import com.example.data.model.Source
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepo {
    suspend fun getNewsFromRemote(
        country: String,
        pageNumber: Int
    ): Flow<Response<BaseEndPointResponse<List<Article>>>>

    fun getNewsFromLocal(): Flow<List<Article>>
    suspend fun addNewToSaved(article: Article)
    suspend fun removeFromSaved(article: Article)

    suspend fun searchNews(searchQuery:String) : Flow<Response<BaseEndPointResponse<List<Article>>>>

    suspend fun getSources() : Flow<Response<BaseEndPointResponse<List<Source>>>>
}