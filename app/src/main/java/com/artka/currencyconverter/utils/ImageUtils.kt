package com.artka.currencyconverter.utils

import com.artka.currencyconverter.R

object ImageUtils {


    @JvmStatic
    fun getImageId(name : String) : Int {
        return when (findCurrency(name.toUpperCase())) {
            Currencies.CAD -> R.drawable.ic_canada
            Currencies.HKD -> R.drawable.ic_hong_kong
            Currencies.ISK -> R.drawable.ic_iceland
            Currencies.PHP -> R.drawable.ic_philippines
            Currencies.DKK -> R.drawable.ic_denmark
            Currencies.HUF -> R.drawable.ic_hungary
            Currencies.CZK -> R.drawable.ic_czech_republic
            Currencies.AUD -> R.drawable.ic_australia
            Currencies.RON -> R.drawable.ic_romania
            Currencies.SEK -> R.drawable.ic_sweden
            Currencies.IDR -> R.drawable.ic_indonesia
            Currencies.INR -> R.drawable.ic_india
            Currencies.BRL -> R.drawable.ic_brazil
            Currencies.RUB -> R.drawable.ic_russia
            Currencies.HRK -> R.drawable.ic_croatia
            Currencies.JPY -> R.drawable.ic_japan
            Currencies.THB -> R.drawable.ic_thailand
            Currencies.CHF -> R.drawable.ic_switzerland
            Currencies.SGD -> R.drawable.ic_singapore
            Currencies.PLN -> R.drawable.ic_republic_of_poland
            Currencies.BGN -> R.drawable.ic_bulgaria
            Currencies.TRY -> R.drawable.ic_turkey
            Currencies.CNY -> R.drawable.ic_china
            Currencies.NOK -> R.drawable.ic_norway
            Currencies.NZD -> R.drawable.ic_new_zealand
            Currencies.ZAR -> R.drawable.ic_south_africa
            Currencies.USD -> R.drawable.ic_united_states_of_america
            Currencies.MXN -> R.drawable.ic_mexico
            Currencies.ILS -> R.drawable.ic_israel
            Currencies.GBP -> R.drawable.ic_united_kingdom
            Currencies.KRW -> R.drawable.ic_south_korea
            Currencies.MYR -> R.drawable.ic_malaysia
            Currencies.EUR -> R.drawable.ic_european_union
            else -> -1
        }
    }

    private fun findCurrency(name : String) : Currencies? {
        return Currencies.values().find { it.name == name }
    }

    enum class Currencies {
        CAD,
        HKD,
        ISK,
        PHP,
        DKK,
        HUF,
        CZK,
        AUD,
        RON,
        SEK,
        IDR,
        INR,
        BRL,
        RUB,
        HRK,
        JPY,
        THB,
        CHF,
        SGD,
        PLN,
        BGN,
        TRY,
        CNY,
        NOK,
        NZD,
        ZAR,
        USD,
        MXN,
        ILS,
        GBP,
        KRW,
        MYR,
        EUR
    }
}

