package com.example.domain.usecases

import com.example.domain.repository.NewsRepository

class GetNewsFromLocal(val newsRepository: NewsRepository) {
    suspend operator fun invoke()= newsRepository.getNewsFromLocal()
}