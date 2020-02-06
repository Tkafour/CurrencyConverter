package com.artka.currencyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artka.currencyconverter.models.Rate

@Database(entities = [Rate::class], version = 1)
abstract class CurrencyDb : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        private var INSTANCE: CurrencyDb? = null
        fun getInstance(context: Context): CurrencyDb? {
            if (INSTANCE == null) {
                synchronized(CurrencyDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyDb::class.java, "currency_converter.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}