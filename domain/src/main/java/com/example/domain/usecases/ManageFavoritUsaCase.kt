package com.example.domain.usecases

import com.example.data.model.Article
import com.example.data.repo.NewsRepo

class ManageFavoritUsaCase(val newsRepo: NewsRepo) {

    /*suspend fun addToFavorit(article: Article)=newsRepo.addNewToFavorit(article)

    suspend operator fun invoke(article: Article)= newsRepo.removeFromFavorit(article)*/
}