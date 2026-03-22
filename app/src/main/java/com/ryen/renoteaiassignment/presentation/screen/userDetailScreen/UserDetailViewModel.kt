package com.ryen.renoteaiassignment.presentation.screen.userDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.domain.usecase.GetUserByIdUseCase
import com.ryen.renoteaiassignment.domain.usecase.ToggleFavoriteUseCase
import com.ryen.renoteaiassignment.presentation.screen.uiState.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserById: GetUserByIdUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Extract userId from SavedStateHandle — matches UserDetailRoute(userId)
    private val userId: Int = checkNotNull(savedStateHandle["userId"])

    val userState: StateFlow<UiState<User>> = getUserById(userId)
        .map { user -> UiState.Success(user) as UiState<User> }
        .catch { e -> emit(UiState.Error(e.message ?: "Failed to load user")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    fun onFavoriteToggle() {
        viewModelScope.launch {
            toggleFavorite(userId)
        }
    }
}