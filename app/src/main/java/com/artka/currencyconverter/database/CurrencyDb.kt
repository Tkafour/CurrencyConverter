package com.artka.currencyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Rate::class], version = 1)
abstract class CurrencyDb : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}