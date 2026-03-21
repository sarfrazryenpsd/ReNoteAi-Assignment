package com.ryen.renoteaiassignment.data.datasource.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json

    @TypeConverter
    fun fromAddress(address: Address): String = json.encodeToString(address)

    @TypeConverter
    fun toAddress(value: String): Address = json.decodeFromString(value)

    @TypeConverter
    fun fromCompany(company: Company): String = json.encodeToString(company)

    @TypeConverter
    fun toCompany(value: String): Company = json.decodeFromString(value)
}