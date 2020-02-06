package com.artka.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artka.currencyconverter.base.BaseViewModel
import com.artka.currencyconverter.interactors.CurrencyInteractor
import com.artka.currencyconverter.models.Rate
import com.artka.currencyconverter.utils.LogUtils.debugLog
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val currencyInteractor: CurrencyInteractor
) : BaseViewModel() {

    private var subscription: Disposable? = null
    private var ratesList = MutableLiveData<List<Rate>>()

    init {
        getExchangeRates()
    }

    fun getExchangeRates() {
        subscription = currencyInteractor.getExchangeRates().subscribe({
            debugLog("rates are $it")
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