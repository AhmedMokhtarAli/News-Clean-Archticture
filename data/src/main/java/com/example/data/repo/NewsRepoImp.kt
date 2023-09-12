package com.example.data.repo

import com.example.data.model.Article
import com.example.data.model.BaseEndPointResponse
import com.example.data.model.NewsResponse
import com.example.data.remote.NewsApi
import com.example.utilis.printToLogD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImp @Inject constructor(val apiService: NewsApi/*, val newsDao: NewsDao*/) : NewsRepo{
    override suspend fun getNewsFromRemote(country: String, pageNumber: Int): Flow<Response<BaseEndPointResponse<List<Article>>>> {
    return    flow {
            emit(     apiService.getNews(
                country/*,pageNumber*/
            ))
        }
    }

//    override fun getNewsFromLocal(): Flow<List<Article>> = newsDao.getFavNews()


   /* override suspend fun searchForNews(searchQuery: String, pageNumber: Int) =
        flow {
            emit(apiService.)
        }
*/
/*
    override suspend fun addNewToFavorit(article: Article) {
//        newsDao.addToFav(article)
    }

    override suspend fun removeFromFavorit(article: Article) {
//        newsDao.removeFromFav(article)
    }
*/

}