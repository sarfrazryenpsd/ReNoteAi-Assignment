package com.ryen.renoteaiassignment.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>  // Flow — auto-updates UI on change

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): Flow<UserEntity>

    @Query("SELECT id FROM users WHERE isFavorite = 1")
    fun getFavoriteUserIds(): Flow<List<Int>>

    @Query("SELECT isFavorite FROM users WHERE id = :id")
    suspend fun isFavorite(id: Int): Boolean

    @Query("UPDATE users SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Int)

    @Upsert  // insert if not exists, update if exists — perfect for refresh
    suspend fun upsertAll(users: List<UserEntity>)
}