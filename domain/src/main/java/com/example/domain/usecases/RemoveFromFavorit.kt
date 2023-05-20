package com.example.domain.usecases

import com.example.domain.entity.Article
import com.example.domain.repository.NewsRepository

class RemoveFromFavorit(val repository: NewsRepository) {
    suspend operator fun invoke(article: Article)= repository.removeFromFavorit(article)
}