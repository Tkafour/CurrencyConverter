package com.artka.currencyconverter.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Rate(
    @PrimaryKey
    val name : String,
    val rate : Double
)