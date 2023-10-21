package com.example.domain.usecases

import com.example.data.model.Article
import com.example.data.repo.NewsRepo
import com.example.domain.transformResponseData
import com.example.utilis.printToLogD
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(val newsRepo: NewsRepo) {
   suspend fun searchWithQuery(searchQuery: String): Flow<List<Article>> {

        return newsRepo.searchNews(searchQuery).transformResponseData {
            emit(it)
        }
    }
}