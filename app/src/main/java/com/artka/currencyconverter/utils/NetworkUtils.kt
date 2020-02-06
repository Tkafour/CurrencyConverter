package com.artka.currencyconverter.utils

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import com.artka.currencyconverter.app.App
import io.reactivex.subjects.BehaviorSubject

object NetworkUtils {

    var networkStateSubject = BehaviorSubject.create<NetworkInfo.State>()

    @JvmStatic
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            ContextCompat.getSystemService(
                App.applicationContext(),
                ConnectivityManager::class.java
            )
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}