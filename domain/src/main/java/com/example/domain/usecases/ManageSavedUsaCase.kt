package com.example.domain.usecases

import com.example.data.model.Article
import com.example.data.repo.NewsRepo

class ManageSavedUsaCase(val newsRepo: NewsRepo) {

    suspend fun addToSaved(article: Article)=newsRepo.addNewToSaved(article)

    suspend  fun removeFromSaved(article: Article)= newsRepo.removeFromSaved(article)
}