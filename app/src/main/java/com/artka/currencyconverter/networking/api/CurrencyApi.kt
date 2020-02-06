package com.artka.currencyconverter.networking.api

import com.artka.currencyconverter.models.CurrencyListModel
import io.reactivex.Single
import retrofit2.http.GET

const val LATEST = "/latest"

interface CurrencyApi {

    @GET(LATEST)
    fun getExchangeRate(): Single<CurrencyListModel>
}