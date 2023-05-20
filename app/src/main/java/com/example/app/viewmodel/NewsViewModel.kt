package com.example.app.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.NewsApplication
import com.example.data.repository.NewsRepoImp
import com.example.domain.entity.NewsResponse
import com.example.domain.usecases.GetNewsFromRemote
import com.example.domain.utilis.NewsResource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val getNewsFromRemote: GetNewsFromRemote, application: NewsApplication)
    : AndroidViewModel(application) {
    private val _news: MutableStateFlow<NewsResource<NewsResponse>?> = MutableStateFlow(null)
    val news=_news
    val newsResponse:NewsResponse?=null
    init {
        getNews()
    }


    private fun getNews(country:String = "us",pageNum:Int=1){
        viewModelScope.launch {
            val response=getNewsFromRemote()
            _news.emit(response)
        }
    }
    }