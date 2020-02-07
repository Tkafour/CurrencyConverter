package com.artka.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artka.currencyconverter.R
import com.artka.currencyconverter.base.BaseViewModel
import com.artka.currencyconverter.database.Rate
import com.artka.currencyconverter.interactors.CurrencyInteractor
import com.artka.currencyconverter.utils.LogUtils.debugLog
import com.artka.currencyconverter.utils.NetworkUtils
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val currencyInteractor: CurrencyInteractor
) : BaseViewModel() {

    private var subscription: Disposable? = null
    private var ratesList = MutableLiveData<List<Rate>>()

    init {
        getData()
    }

    fun getData() {
        if (NetworkUtils.isNetworkAvailable()) {
            getExchangeRates()
        } else {
            getCachedExchangeRates()
        }
    }

    private fun getExchangeRates() {
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

    private fun getCachedExchangeRates() {
        subscription?.dispose()
        subscription = currencyInteractor.getCachedExchangeRates()
            .subscribe({
                val names = it.map { it.name }
                debugLog("rates are $names")
                if (it.isNullOrEmpty()) {
                    errorMessageLiveData.postValue(R.string.error_network_error)
                }
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

    fun calculateAmount(
        firstCurrency: String,
        secondCurrency: String,
        amount: Double
    ): Single<String> {
        val rates = ratesList.value
        val firstRate = rates?.find { it.name == firstCurrency }?.rate ?: 1.0
        val secondRate = rates?.find { it.name == secondCurrency }?.rate ?: 1.0
        return currencyInteractor.calculateAmount(firstRate, secondRate, amount)
    }

}