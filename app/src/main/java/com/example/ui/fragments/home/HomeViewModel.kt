package com.example.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.base.BaseViewModel
import com.example.domain.usecases.GetNewsUseCase
import com.example.domain.usecases.ManageSavedUsaCase
import com.example.utilis.api.NetworkState
import com.example.utilis.printToLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) :
    BaseViewModel() {
    private val _topHeadLines = MutableSharedFlow<NetworkState>()
    val topHeadLines get() = _topHeadLines.asSharedFlow()

    private val _sources = MutableSharedFlow<NetworkState>()
    val sources get() = _sources.asSharedFlow()


    fun getTopHeadlines(country: String = "us", pageNum: Int = 1) {
        executeSharedApi(_topHeadLines) {
            getNewsUseCase.getNewsFromRemote(country, pageNum)
                .onStart { _topHeadLines.emit(NetworkState.Loading) }
                .onCompletion { _topHeadLines.emit(NetworkState.DisMissLoading) }
                .catch { _topHeadLines.emit(NetworkState.Erorr(it)) }
                .collectLatest { _topHeadLines.emit(NetworkState.Success(it)) }
        }
    }

    fun getSources() {
        executeSharedApi(_sources) {
            getNewsUseCase.getSources()
                .onStart { _sources.emit(NetworkState.Loading) }
                .onCompletion { _sources.emit(NetworkState.DisMissLoading) }
                .catch {
                    _sources.emit(NetworkState.Erorr(it)) }
                .collectLatest { _sources.emit(NetworkState.Success(it))

                }
        }
    }
}