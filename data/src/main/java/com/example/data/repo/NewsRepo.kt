package com.example.data.repo

import com.example.data.model.Article
import com.example.data.model.BaseEndPointResponse
import com.example.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepo {
    suspend fun getNewsFromRemote(country:String, pageNumber:Int): Flow<Response<BaseEndPointResponse<List<Article>>>>
/*
    suspend fun searchForNews(searchQuery:String,pageNumber: Int): Flow<Response<BaseEndPointResponse<Article>>>
*/
//    fun getNewsFromLocal(): Flow<List<Article>>
 /*   suspend fun addNewToFavorit(article: Article)
    suspend fun removeFromFavorit(article: Article)*/

}