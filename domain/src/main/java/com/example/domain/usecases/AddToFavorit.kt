package com.example.domain.usecases

import com.example.domain.entity.Article
import com.example.domain.repository.NewsRepository

class AddToFavorit(val newsRepository: NewsRepository) {
    suspend operator fun invoke(article: Article)=newsRepository.addNewToFavorit(article)
}