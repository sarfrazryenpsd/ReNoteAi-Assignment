package com.ryen.renoteaiassignment.domain.usecase

import com.ryen.renoteaiassignment.domain.repository.UserRepository
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.refreshUsers()
}