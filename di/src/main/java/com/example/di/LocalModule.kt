package com.example.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

 /*   @Provides
    fun provideDao(newsDataBase: NewsDataBase):NewsDao=newsDataBase.newsDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        NewsDataBase::class.java,
        "NewsDatabase"
    ).build()*/
}