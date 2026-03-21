package com.ryen.renoteaiassignment.domain.repository

import com.ryen.renoteaiassignment.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<User>>

    fun getUserById(id: Int): Flow<User>

    suspend fun refreshUsers()

    suspend fun toggleFavourite(id: Int)
}