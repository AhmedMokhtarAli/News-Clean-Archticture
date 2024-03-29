package com.example.ui.fragments.TopHeadLines

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.data.model.Article
import com.example.domain.usecases.GetNewsUseCase
import com.example.domain.usecases.ManageSavedUsaCase
import com.example.utilis.api.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadLinesViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val manageSavedUsaCase: ManageSavedUsaCase
) :
    BaseViewModel() {
    private val _newsFromApi = MutableSharedFlow<NetworkState>()
    val newsFromApi get() = _newsFromApi.asSharedFlow()
    fun getNews(country: String = "us", pageNum: Int = 1) {
        executeSharedApi(_newsFromApi) {
            getNewsUseCase.getNewsFromRemote(country, pageNum)
                .onStart { _newsFromApi.emit(NetworkState.Loading) }
                .onCompletion { _newsFromApi.emit(NetworkState.DisMissLoading) }
                .catch { _newsFromApi.emit(NetworkState.Erorr(it)) }
                .collectLatest { _newsFromApi.emit(NetworkState.Success(it)) }
        }
    }

    fun saveToLocal(article: Article) = viewModelScope.launch {
        manageSavedUsaCase.addToSaved(article)
    }
}