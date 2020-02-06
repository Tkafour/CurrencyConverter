package com.artka.currencyconverter.interactors

import com.artka.currencyconverter.database.CurrencyDbRepository
import com.artka.currencyconverter.models.Rate
import com.artka.currencyconverter.networking.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyInteractor @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val currencyDbRepository: CurrencyDbRepository
) {
    fun getExchangeRates(): Single<List<Rate>> {
        return currencyRepository.getExchangeRates().flatMap {
            val rates = it.rates
            if (!rates?.rates.isNullOrEmpty()) {
                currencyDbRepository.insertCurrencyList(rates?.rates ?: mutableListOf())
                Single.just(rates?.rates)
            } else {
                currencyDbRepository.getRates()
            }
        }
    }

}