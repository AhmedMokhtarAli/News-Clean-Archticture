package com.example.utilis.api

sealed class NetworkState {
    data class Success<out T>(val data: T) : NetworkState()

    data class Erorr(val throwable: Throwable) : NetworkState()

    object Loading : NetworkState()

    object DisMissLoading : NetworkState()
}