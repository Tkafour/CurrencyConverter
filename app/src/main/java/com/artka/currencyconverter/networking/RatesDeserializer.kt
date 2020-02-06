package com.artka.currencyconverter.networking

import com.artka.currencyconverter.models.CurrencyListModel
import com.artka.currencyconverter.models.Rate
import com.artka.currencyconverter.models.Rates
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class RatesDeserializer : JsonDeserializer<CurrencyListModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyListModel {
        val jsonObject = json?.asJsonObject
        val ratesObject = jsonObject?.get("rates") as? JsonObject
        val keys = ratesObject?.keySet()?.toList()
        val rates = mutableListOf<Rate>()
        keys?.forEachIndexed { index, s ->
            val key = keys[index]
            val rate = Rate(name = key, rate = ratesObject.get(key).asDouble)
            rates.add(rate)
        }

        val base = jsonObject?.get("base")?.asString
        val date = jsonObject?.get("date")?.asString

        return CurrencyListModel(base = base, date = date, rates = Rates(rates))
    }
}