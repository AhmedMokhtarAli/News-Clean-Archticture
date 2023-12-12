package com.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilis.api.ConnectivtyObserver
import com.example.utilis.api.NetworkState
import com.example.utilis.printToLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    private val parentJob= Job()


    fun handelShared(state:MutableSharedFlow<NetworkState>):CoroutineExceptionHandler{
        return CoroutineExceptionHandler{coroutineContext, throwable ->
//            throwable.printToLogD("news-tag")

        }
    }

    fun executeSharedApi(state: MutableSharedFlow<NetworkState>, job:Job=parentJob, action:suspend () -> Unit){
        viewModelScope.launch (handelShared(state)){
            action()
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}