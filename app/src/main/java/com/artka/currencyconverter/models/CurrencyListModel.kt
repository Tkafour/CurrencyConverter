package com.artka.currencyconverter.models

import com.artka.currencyconverter.database.Rate

data class CurrencyListModel(
    val base: String ?= "",
    val date: String ?= "",
    val rates: List<Rate> ?= listOf()
)



