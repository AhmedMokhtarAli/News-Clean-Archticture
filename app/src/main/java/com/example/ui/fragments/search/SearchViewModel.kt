package com.example.ui.fragments.search

import com.example.base.BaseViewModel
import com.example.domain.usecases.SearchNewsUseCase
import com.example.utilis.api.NetworkState
import com.example.utilis.printToLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchNewsUseCase: SearchNewsUseCase) :
    BaseViewModel() {
    private val _searchResult = MutableSharedFlow<NetworkState>()
    val searchResult get() = _searchResult.asSharedFlow()

    fun searchNews(searchQuery: String) = executeSharedApi(_searchResult) {
        searchNewsUseCase.searchWithQuery(searchQuery).onStart { _searchResult.emit(NetworkState.Loading) }
            .onCompletion { _searchResult.emit(NetworkState.DisMissLoading) }
            .catch { _searchResult.emit(NetworkState.Erorr(it)) }
            .collectLatest { _searchResult.emit(NetworkState.Success(it)) }
    }
}