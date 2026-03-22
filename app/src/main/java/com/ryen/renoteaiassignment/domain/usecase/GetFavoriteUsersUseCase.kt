package com.ryen.renoteaiassignment.domain.usecase

import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavoriteUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> = repository.getFavoriteUsers().flowOn(Dispatchers.IO)
}