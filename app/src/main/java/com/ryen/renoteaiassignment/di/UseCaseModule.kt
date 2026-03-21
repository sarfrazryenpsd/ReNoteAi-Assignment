package com.ryen.renoteaiassignment.di

import com.ryen.renoteaiassignment.domain.repository.UserRepository
import com.ryen.renoteaiassignment.domain.usecase.GetUsersUseCase
import com.ryen.renoteaiassignment.domain.usecase.RefreshUsersUseCase
import com.ryen.renoteaiassignment.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repository: UserRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshUsersUseCase(repository: UserRepository): RefreshUsersUseCase {
        return RefreshUsersUseCase(repository)
    }

}