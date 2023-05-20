package com.example.domain.usecases

import com.example.domain.repository.NewsRepository

class SearchNews(val newsRepository: NewsRepository) {
    suspend operator fun  invoke(searchQuery: String, pageNumber: Int)= newsRepository.searchForNews(searchQuery,pageNumber)
}