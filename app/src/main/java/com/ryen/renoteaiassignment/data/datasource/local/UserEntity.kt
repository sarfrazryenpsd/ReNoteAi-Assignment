package com.ryen.renoteaiassignment.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val username: String,
    val isFavorite: Boolean,
    val address: Address,
    val company: Company
)

@Serializable
data class Address(
    val city: String
)

@Serializable
data class Company(
    val name: String
)