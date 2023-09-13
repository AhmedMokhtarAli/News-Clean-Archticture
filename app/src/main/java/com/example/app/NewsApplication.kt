package com.example.app

import android.app.Application
import com.example.newscleanarch.BuildConfig
import com.example.utilis.api.ConstantApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application (){
    override fun onCreate() {
        super.onCreate()
       /* ConstantApi.BASE_URL= BuildConfig.BASE_URL
        ConstantApi.API_KEY =BuildConfig.API_KEY*/
    }
}