package com.ryen.renoteaiassignment.domain.usecase

import com.ryen.renoteaiassignment.domain.repository.UserRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) = repository.toggleFavourite(id)
}