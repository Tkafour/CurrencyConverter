package com.artka.currencyconverter.networking

import com.artka.currencyconverter.models.CurrencyListModel
import com.artka.currencyconverter.networking.api.CurrencyApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(private val currencyApi: CurrencyApi) {

    fun getExchangeRates(): Single<CurrencyListModel> {
        return currencyApi.getExchangeRate().subscribeOn(Schedulers.io())
    }

}