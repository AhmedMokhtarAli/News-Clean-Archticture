package com.example.domain.usecases

import com.example.data.model.Article
import com.example.data.repo.NewsRepo
import com.example.domain.transformResponseData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(val newsRepo: NewsRepo) {

    suspend fun getNewsFromRemote(country:String="us",pageNumber:Int=1): Flow<List<Article>> =
        newsRepo.getNewsFromRemote(country,pageNumber).transformResponseData { emit(it) }

    fun getFromNewsFromLocal() = newsRepo.getNewsFromLocal()
}