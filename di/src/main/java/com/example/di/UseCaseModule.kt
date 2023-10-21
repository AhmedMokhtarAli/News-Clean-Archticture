package com.example.di

import com.example.data.repo.NewsRepo
import com.example.domain.usecases.GetNewsUseCase
import com.example.domain.usecases.ManageSavedUsaCase
import com.example.domain.usecases.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetNews(newsRepo: NewsRepo):GetNewsUseCase=
        GetNewsUseCase(newsRepo)

    @Provides
    fun providesManageSavedArticles(newsRepo: NewsRepo)=
        ManageSavedUsaCase(newsRepo)
    @Provides
    fun providesSearchNews(newsRepo: NewsRepo):SearchNewsUseCase=
        SearchNewsUseCase(newsRepo)
}