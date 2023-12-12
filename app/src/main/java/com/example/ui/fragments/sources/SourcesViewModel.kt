package com.example.ui.fragments.sources

import com.example.base.BaseViewModel
import com.example.domain.usecases.GetNewsUseCase
import com.example.domain.usecases.SearchNewsUseCase
import com.example.utilis.api.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : BaseViewModel() {
    private val _sourceArticles = MutableSharedFlow<NetworkState>()
    val sourceArticles get() = _sourceArticles.asSharedFlow()

    fun getSourceArticles(sourceId: String) {
        executeSharedApi(_sourceArticles) {
            searchNewsUseCase.searchWithQuery(sourceId)
                .onStart { _sourceArticles.emit(NetworkState.Loading) }
                .onCompletion { _sourceArticles.emit(NetworkState.DisMissLoading) }
                .catch { _sourceArticles.emit(NetworkState.Erorr(it)) }
                .collectLatest { _sourceArticles.emit(NetworkState.Success(it)) }
        }
    }
}
