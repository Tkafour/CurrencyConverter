package com.artka.currencyconverter.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.artka.currencyconverter.di.components.ApplicationComponent
import com.artka.currencyconverter.di.components.DaggerApplicationComponent

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
        DaggerApplicationComponent.builder().build()
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