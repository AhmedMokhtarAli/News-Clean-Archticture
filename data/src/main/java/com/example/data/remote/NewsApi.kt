package com.example.data.remote

import com.example.data.model.Article
import com.example.data.model.BaseEndPointResponse
import com.example.utilis.api.ConstantApi.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(
       @Query ("country") countryCode:String="us",
       @Query ("apiKey") api_key:String=API_KEY
                ) : Response<BaseEndPointResponse<List<Article>>>

    @GET("everything")
    suspend fun searchNew(
        @Query("q") searchQuery:String,
        @Query("apiKey") api_key: String= API_KEY
    ) : Response<BaseEndPointResponse<List<Article>>>
}