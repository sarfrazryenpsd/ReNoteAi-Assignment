package com.ryen.renoteaiassignment.presentation.screen.userListScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.renoteaiassignment.domain.model.User
import com.ryen.renoteaiassignment.domain.usecase.GetUsersUseCase
import com.ryen.renoteaiassignment.domain.usecase.RefreshUsersUseCase
import com.ryen.renoteaiassignment.domain.usecase.ToggleFavoriteUseCase
import com.ryen.renoteaiassignment.presentation.screen.uiState.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserListUiState(
    val usersState: UiState<List<User>> = UiState.Loading,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,
    val refreshError: String? = null
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsers: GetUsersUseCase,
    private val refreshUsers: RefreshUsersUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _refreshError = MutableStateFlow<String?>(null)
    val refreshError: StateFlow<String?> = _refreshError.asStateFlow()

    val usersState: StateFlow<UiState<List<User>>> = _searchQuery
        .debounce(300L)
        .flatMapLatest { query ->
            getUsers()
                .map { users ->
                    val filtered = if (query.isBlank()) users
                    else users.filter { user ->
                        user.name.split(" ").any { word ->
                            word.startsWith(query.trim(), ignoreCase = true)
                        }
                    }
                    UiState.Success(filtered) as UiState<List<User>>
                }
                .catch { e ->
                    emit(UiState.Error(e.message ?: "Failed to load users"))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    init {
        refresh()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.update { query }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.update { true }
            _refreshError.update { null }

            refreshUsers()
                .onFailure { e ->
                    _refreshError.update { e.message ?: "Refresh failed" }
                }

            _isRefreshing.update { false }
        }
    }

    fun simulateFailure() {
        _refreshError.update { "Simulated network failure — no internet connection" }
    }

    fun onFavoriteToggle(userId: Int) {
        viewModelScope.launch {
            toggleFavorite(userId)
        }
    }

    fun dismissRefreshError() {
        _refreshError.update { null }
    }
}