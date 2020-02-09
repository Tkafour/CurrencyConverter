package com.artka.currencyconverter.utils

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.artka.currencyconverter.app.App

object NetworkUtils {

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