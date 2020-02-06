package com.artka.currencyconverter.di.modules

import com.artka.currencyconverter.app.App
import com.artka.currencyconverter.database.CurrencyDb
import com.artka.currencyconverter.database.CurrencyDbRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("unused")
object RoomModule {

    @Singleton
    @Provides
    @JvmStatic
    internal fun provideCurrencyDbRepository() : CurrencyDbRepository {
        return CurrencyDbRepository(provideRoom().currencyDao())
    }

    @Singleton
    @Provides
    @JvmStatic
    internal fun provideRoom() : CurrencyDb {
        return CurrencyDb.getInstance(App.applicationContext())!!
    }
}