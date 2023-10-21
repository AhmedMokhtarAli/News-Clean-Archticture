package com.example.data.repo

import com.example.data.local.NewsDao
import com.example.data.model.Article
import com.example.data.model.BaseEndPointResponse
import com.example.data.remote.NewsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImp @Inject constructor(val apiService: NewsApi, val newsDao: NewsDao) : NewsRepo {
    override suspend fun getNewsFromRemote(
        country: String,
        pageNumber: Int
    ): Flow<Response<BaseEndPointResponse<List<Article>>>> {
        return flow {
            emit(
                apiService.getNews(
                    country
                )
            )
        }
    }
    override suspend fun searchNews(searchQuery: String): Flow<Response<BaseEndPointResponse<List<Article>>>> {
        return flow { emit(apiService.searchNew(searchQuery)) }
    }

    override fun getNewsFromLocal(): Flow<List<Article>> = newsDao.getAllArticles()

    override suspend fun addNewToSaved(article: Article) {
        newsDao.addToSaved(article)
    }

    override suspend fun removeFromSaved(article: Article) {
        newsDao.removeFromSaved(article)
    }

}