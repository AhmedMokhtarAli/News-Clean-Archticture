package com.example.domain.usecases

import com.example.data.repo.NewsRepo

class SearchNewsUseCase(val newsRepo: NewsRepo) {
//    suspend operator fun  invoke(searchQuery: String, pageNumber: Int)= newsRepo.searchForNews(searchQuery,pageNumber)
}