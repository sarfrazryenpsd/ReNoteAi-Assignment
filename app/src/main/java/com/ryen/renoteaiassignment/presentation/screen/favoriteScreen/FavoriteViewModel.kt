package com.ryen.renoteaiassignment.presentation.screen.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.domain.usecase.GetFavoriteUsersUseCase
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
class FavoritesViewModel @Inject constructor(
    getFavoriteUsers: GetFavoriteUsersUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    val favoritesState: StateFlow<UiState<List<User>>> = getFavoriteUsers()
        .map { users -> UiState.Success(users) as UiState<List<User>> }
        .catch { e -> emit(UiState.Error(e.message ?: "Failed to load favorites")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    fun onFavoriteToggle(userId: Int) {
        viewModelScope.launch {
            toggleFavorite(userId)
        }
    }
}