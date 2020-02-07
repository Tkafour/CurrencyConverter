package com.artka.currencyconverter.database

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyDbRepository @Inject constructor (private val currencyDao: CurrencyDao) {

    fun insertCurrencyList(rates : List<Rate>)  {
        currencyDao.insertRates(rates)
    }

    fun getRates() : Single<List<Rate>> {
        return currencyDao.getRates().subscribeOn(Schedulers.io())
    }

}