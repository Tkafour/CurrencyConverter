package com.artka.currencyconverter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CurrencyListModel(
    val base: String ?= "",
    val date: String ?= "",
    val rates: Rates ?= Rates(mutableListOf())
)
@Entity(tableName = "currency")
data class Rate(
    @PrimaryKey
    val name : String,
    val rate : Double
)

data class Rates(
    val rates : List<Rate>
)

