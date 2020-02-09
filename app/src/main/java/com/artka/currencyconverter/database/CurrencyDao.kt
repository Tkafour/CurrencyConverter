package com.artka.currencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rates : List<Rate>)

    @Query("SELECT * from currency ORDER BY name ASC")
    fun getRates() : Single<List<Rate>>
}