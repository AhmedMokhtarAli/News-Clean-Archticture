package com.example.domain.usecases

import com.example.data.model.Article
import com.example.data.model.ArticleSource
import com.example.data.model.Source
import com.example.data.repo.NewsRepo
import com.example.domain.transformResponseData
import com.example.utilis.printToLogD
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(val newsRepo: NewsRepo) {

    suspend fun getNewsFromRemote(
        country: String = "us",
        pageNumber: Int = 1
    ): Flow<List<Article>> =
        newsRepo.getNewsFromRemote(country, pageNumber).transformResponseData {
            emit(it)
        }


    suspend fun getSources(): Flow<List<Source>> =
        newsRepo.getSources().transformResponseData { emit(it) }

    fun getFromNewsFromLocal() = newsRepo.getNewsFromLocal()

}