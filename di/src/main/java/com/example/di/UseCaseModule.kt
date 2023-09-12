package com.example.di

import com.example.data.repo.NewsRepo
import com.example.domain.usecases.GetNewsUseCase
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

 /*   @Provides
    fun providesSearchNews(newsRepo: NewsRepository):SearchNews=
        SearchNews(newsRepo)
    @Provides
    fun providesGetNewsFromLocal(newsRepo: NewsRepository):GetNews=
        GetNews(newsRepo)
    @Provides
    fun providesAddToFav(newsRepo: NewsRepository):AddToFavorit=
        AddToFavorit(newsRepo)
    @Provides
    fun providesRemoveFromFav(newsRepo: NewsRepository):RemoveFromFavorit=
        RemoveFromFavorit(newsRepo)*/
}