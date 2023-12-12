package com.example.utilis.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InternetConnectionObserver @Inject constructor(private val context:Context) : ConnectivtyObserver{

    private val connectivtyManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun observe(): Flow<ConnectivtyObserver.ConnectionState> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {  send(ConnectivtyObserver.ConnectionState.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivtyObserver.ConnectionState.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivtyObserver.ConnectionState.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivtyObserver.ConnectionState.UnAvailable) }
                }
            }
            connectivtyManager.registerDefaultNetworkCallback(callback)
            awaitClose{
                connectivtyManager.unregisterNetworkCallback(callback)
            }

        }
    }
}

interface ConnectivtyObserver{
    fun observe(): Flow<ConnectionState>
    enum class ConnectionState{
        Available,UnAvailable,Lost,Losing
    }
}
