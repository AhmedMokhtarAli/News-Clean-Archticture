package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.NewsDao
import com.example.data.local.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDao(newsDataBase: NewsDataBase): NewsDao =newsDataBase.newsDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
        context,
        NewsDataBase::class.java,
        "NewsDatabase"
    ).build()
}