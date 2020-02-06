package com.artka.currencyconverter.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import androidx.multidex.MultiDexApplication
import com.artka.currencyconverter.di.components.ApplicationComponent
import com.artka.currencyconverter.di.components.DaggerApplicationComponent
import com.artka.currencyconverter.receivers.NetworkReceiver

class App : MultiDexApplication() {

    private var networkReceiver: NetworkReceiver? = null

    override fun onCreate() {
        super.onCreate()
        context = this

        DaggerApplicationComponent.builder().build()

        networkReceiver = NetworkReceiver()
        applicationContext.registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    companion object {

        private val applicationComponent = DaggerApplicationComponent.builder().build()

        @JvmStatic
        lateinit var context: Application

        fun applicationContext(): Context {
            return context
        }

        fun applicationComponent() : ApplicationComponent {
            return applicationComponent
        }
    }
}