package com.example.data.repository

import com.example.data.local.NewsDao
import com.example.data.remote.ApiService
import com.example.domain.entity.Article
import com.example.domain.entity.NewsResponse
import com.example.domain.repository.NewsRepository
import com.example.domain.utilis.NewsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class NewsRepoImp(val apiService: ApiService,val newsDao: NewsDao) : NewsRepository{
    override suspend fun getNewsFromRemote(country: String, pageNumber: Int): NewsResource<NewsResponse> =
        apiService.getNews(
        country,pageNumber
    )

    override fun getNewsFromLocal(): Flow<List<Article>> = newsDao.getFavNews()


    override suspend fun searchForNews(searchQuery: String, pageNumber: Int): NewsResource<NewsResponse>  =
        apiService.getNews(
            searchQuery,pageNumber
        )

    override suspend fun addNewToFavorit(article: Article) {
        newsDao.addToFav(article)
    }

    override suspend fun removeFromFavorit(article: Article) {
        newsDao.removeFromFav(article)
    }

}