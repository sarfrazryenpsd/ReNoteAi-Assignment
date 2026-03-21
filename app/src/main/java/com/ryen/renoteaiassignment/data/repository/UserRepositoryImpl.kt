package com.ryen.renoteaiassignment.data.repository

import com.ryen.renoteaiassignment.data.datasource.local.UserDao
import com.ryen.renoteaiassignment.data.datasource.remote.UserRemoteDatasource
import com.ryen.renoteaiassignment.data.mappers.toUser
import com.ryen.renoteaiassignment.data.mappers.toUserEntity
import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.domain.repository.UserRepository
import com.ryen.renoteaiassignment.data.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDatasource: UserRemoteDatasource,
    private val userDao: UserDao
) : UserRepository {
    override fun getUsers(): Flow<List<User>>  =
        userDao.getAllUsers().map { entities ->
            entities.map { it.toUser() }
        }

    override fun getUserById(id: Int): Flow<User> =
        userDao.getUserById(id).map { it.toUser() }


    override suspend fun refreshUsers(): Result<Unit> {
        return when(val result = remoteDatasource.invoke()){
            is NetworkResult.Success -> {
                val favoriteIds = userDao.getFavoriteUserIds().first().toSet()

                val userEntities = result.data.map { dto ->
                    dto.toUserEntity(isFavorite = favoriteIds.contains(dto.id))
                }

                userDao.upsertAll(userEntities)
                Result.success(Unit)
            }
            is NetworkResult.Error -> {
                Result.failure(Exception(result.message))
            }
            else -> Result.failure(Exception("Unknown error"))
        }

    }

    override suspend fun toggleFavourite(id: Int) =
        userDao.toggleFavorite(id)


}