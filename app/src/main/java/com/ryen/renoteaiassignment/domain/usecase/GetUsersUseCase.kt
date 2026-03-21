package com.ryen.renoteaiassignment.domain.usecase

import com.ryen.renoteaiassignment.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.getUsers()
}