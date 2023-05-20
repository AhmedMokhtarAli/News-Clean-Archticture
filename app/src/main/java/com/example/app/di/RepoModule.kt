package com.example.app.di

import com.example.data.local.NewsDao
import com.example.data.remote.ApiService
import com.example.data.repository.NewsRepoImp
import com.example.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(apiService: ApiService,newsDao: NewsDao): NewsRepository = NewsRepoImp(apiService,newsDao)
}