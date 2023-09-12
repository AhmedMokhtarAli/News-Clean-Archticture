package com.example.di

import com.example.data.repo.NewsRepo
import com.example.data.repo.NewsRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun providesNewsRepo(newsRepoImp: NewsRepoImp) : NewsRepo
}