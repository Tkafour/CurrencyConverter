package com.artka.currencyconverter.di.components

import com.artka.currencyconverter.di.modules.NetworkModule
import com.artka.currencyconverter.di.modules.RoomModule
import com.artka.currencyconverter.viewmodels.ViewModelFactory
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [NetworkModule::class, RoomModule::class])
interface ApplicationComponent {
    fun inject(viewModelFactory: ViewModelFactory)
}