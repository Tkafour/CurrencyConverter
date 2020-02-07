package com.artka.currencyconverter.di.modules

import androidx.room.Room
import com.artka.currencyconverter.app.App
import com.artka.currencyconverter.database.CurrencyDb
import com.artka.currencyconverter.database.CurrencyDbRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("unused")
object RoomModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun provideCurrencyDbRepository(): CurrencyDbRepository {
        return CurrencyDbRepository(provideRoom().currencyDao())
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun provideRoom(): CurrencyDb {
        return Room.databaseBuilder(
            App.applicationContext(),
            CurrencyDb::class.java,
            "currency_converter.db"
        )
            .build()
    }
}