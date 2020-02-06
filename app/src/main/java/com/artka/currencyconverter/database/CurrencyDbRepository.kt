package com.artka.currencyconverter.database

import com.artka.currencyconverter.models.Rate
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyDbRepository @Inject constructor (private val currencyDao: CurrencyDao) {

    fun insertCurrencyList(rates : List<Rate>) {
        currencyDao.insertCurrency(rates)
    }

    fun getRates() : Single<List<Rate>> {
        return Single.just("").observeOn(Schedulers.io())
            .flatMap { Single.just(currencyDao.getEventsList()) }
    }

}