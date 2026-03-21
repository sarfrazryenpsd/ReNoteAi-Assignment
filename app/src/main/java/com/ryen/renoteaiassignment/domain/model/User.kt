package com.ryen.renoteaiassignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val username: String,
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