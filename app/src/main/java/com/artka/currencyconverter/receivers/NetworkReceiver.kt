package com.artka.currencyconverter.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.artka.currencyconverter.utils.LogUtils.debugLog
import com.artka.currencyconverter.utils.NetworkUtils

class NetworkReceiver : BroadcastReceiver() {
    private var previousState: NetworkInfo.State? = null

    override fun onReceive(context: Context, intent: Intent) {

        debugLog("NetworkState changed")

        val systemService =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = systemService.activeNetworkInfo
        previousState = if (activeNetworkInfo != null) {
            val state = activeNetworkInfo.state
            if (state == NetworkInfo.State.CONNECTED && previousState != null && previousState != NetworkInfo.State.CONNECTED)
                NetworkUtils.networkStateSubject.onNext(state)
            state
        } else {
            NetworkUtils.networkStateSubject.onNext(NetworkInfo.State.DISCONNECTED)
            NetworkInfo.State.DISCONNECTED
        }
    }
}