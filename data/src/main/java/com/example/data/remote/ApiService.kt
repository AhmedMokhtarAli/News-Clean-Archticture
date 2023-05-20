package com.example.data.remote

import com.example.data.utilis.Constant.Companion.API_KEY
import com.example.domain.entity.NewsResponse
import com.example.domain.utilis.NewsResource
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query ("country") countryCode:String = "us",
        @Query ("page") pageNumber:Int=1,
        @Query("apiKey") apiKey:String=API_KEY
    ): NewsResource<NewsResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query ("q") searchQuery:String,
        @Query ("page") pageNumber:Int=1,
        @Query("apiKey") apiKey:String=API_KEY
    ):NewsResource<NewsResponse>

}