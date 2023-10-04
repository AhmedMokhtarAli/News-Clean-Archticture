package com.example.di

import com.example.data.remote.NewsApi
import com.example.utilis.BuildConfig
import com.example.utilis.api.ConstantApi.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun providesOkhttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,baseUrl:String) = Retrofit
        .Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    fun provideApiInterface(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)
}