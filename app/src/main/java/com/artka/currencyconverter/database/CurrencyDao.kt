package com.artka.currencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artka.currencyconverter.models.Rate

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(rates : List<Rate>)

    @Query("SELECT * from currency ORDER BY name ASC")
    fun getEventsList() : List<Rate>
}