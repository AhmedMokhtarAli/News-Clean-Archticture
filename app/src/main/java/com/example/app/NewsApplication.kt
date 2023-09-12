package com.example.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application (){
    override fun onCreate() {
        super.onCreate()
//        ConstantApi.BASE_URL=BuildConfig.BASE_URL
    }
}