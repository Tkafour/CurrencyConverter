package com.artka.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artka.currencyconverter.base.BaseViewModel
import com.artka.currencyconverter.interactors.CurrencyInteractor
import com.artka.currencyconverter.models.Rate
import com.artka.currencyconverter.utils.LogUtils.debugLog
import com.artka.currencyconverter.utils.NetworkUtils
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val currencyInteractor: CurrencyInteractor
) : BaseViewModel() {

    private var subscription: Disposable? = null
    private var ratesList = MutableLiveData<List<Rate>>()

    init {
        if (NetworkUtils.isNetworkAvailable()) {
            getExchangeRates()
        } else {
            getCachedExchangeRates()
        }
    }

    fun getExchangeRates() {
        subscription?.dispose()
        subscription = currencyInteractor.getExchangeRates()
            .doOnSubscribe { onLoadStart() }
            .doOnError { onLoadFinish() }
            .doOnSuccess { onLoadFinish() }
            .subscribe({
            val names = it.map { it.name }
            debugLog("rates are $names")
            ratesList.postValue(it)
        }, {
            it.printStackTrace()
            errorLiveData.postValue(it)
        })
        subscription?.let { compositeDisposable.add(it) }
    }

    fun getCachedExchangeRates() {
        subscription?.dispose()
        subscription = currencyInteractor.getCachedExchangeRates()
            .subscribe({
                val names = it.map { it.name }
                debugLog("rates are $names")
                ratesList.postValue(it)
            }, {
                it.printStackTrace()
                errorLiveData.postValue(it)
            })
        subscription?.let { compositeDisposable.add(it) }
    }

    fun getRates(): LiveData<List<Rate>> {
        return ratesList
    }

}