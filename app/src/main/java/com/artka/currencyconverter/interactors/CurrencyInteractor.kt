package com.artka.currencyconverter.interactors

import com.artka.currencyconverter.database.CurrencyDbRepository
import com.artka.currencyconverter.database.Rate
import com.artka.currencyconverter.networking.CurrencyRepository
import io.reactivex.Single
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyInteractor @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val currencyDbRepository: CurrencyDbRepository
) {

    private val df = DecimalFormat("###.##")

    fun getExchangeRates(): Single<List<Rate>> {
        return currencyRepository.getExchangeRates().flatMap {
            val rates = it.rates ?: mutableListOf()
            if (!rates.isNullOrEmpty()) {
                currencyDbRepository.insertCurrencyList(rates)
                Single.just(rates)
            } else {
                currencyDbRepository.getRates()
            }
        }
    }

    fun getCachedExchangeRates(): Single<List<Rate>> {
        return currencyDbRepository.getRates()
    }

    fun calculateAmount(firstRate: Double, secondRate: Double, amount: Double): Single<String> {
        val formattedAmount = df.format(amount * (secondRate / firstRate))
        return Single.just(formattedAmount)
    }
}