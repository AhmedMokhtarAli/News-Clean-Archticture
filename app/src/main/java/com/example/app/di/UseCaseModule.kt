package com.example.app.di

import com.example.data.repository.NewsRepoImp
import com.example.domain.repository.NewsRepository
import com.example.domain.usecases.AddToFavorit
import com.example.domain.usecases.GetNewsFromLocal
import com.example.domain.usecases.GetNewsFromRemote
import com.example.domain.usecases.RemoveFromFavorit
import com.example.domain.usecases.SearchNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetNewsFromRemote(newsRepo: NewsRepository):GetNewsFromRemote=
        GetNewsFromRemote(newsRepo)

    @Provides
    fun providesSearchNews(newsRepo: NewsRepository):SearchNews=
        SearchNews(newsRepo)
    @Provides
    fun providesGetNewsFromLocal(newsRepo: NewsRepository):GetNewsFromLocal=
        GetNewsFromLocal(newsRepo)
    @Provides
    fun providesAddToFav(newsRepo: NewsRepository):AddToFavorit=
        AddToFavorit(newsRepo)
    @Provides
    fun providesRemoveFromFav(newsRepo: NewsRepository):RemoveFromFavorit=
        RemoveFromFavorit(newsRepo)
}