package com.ryen.renoteaiassignment.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val username: String,
    val isFavorite: Boolean,
    val city: String,
    val companyName: String
)