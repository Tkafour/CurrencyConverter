package com.artka.currencyconverter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artka.currencyconverter.app.App
import com.artka.currencyconverter.interactors.CurrencyInteractor
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var currencyInteractor: CurrencyInteractor

    init {
        inject()
    }

    private fun inject() {
        App.applicationComponent().inject(this)
    }


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(currencyInteractor) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}