package com.ryen.renoteaiassignment.presentation.screen.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ryen.renoteaiassignment.presentation.component.UserListItem
import com.ryen.renoteaiassignment.presentation.screen.EmptyScreen
import com.ryen.renoteaiassignment.presentation.screen.ErrorScreen
import com.ryen.renoteaiassignment.presentation.screen.LoadingScreen
import com.ryen.renoteaiassignment.presentation.screen.uiState.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onUserClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState by viewModel.favoritesState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        when (val state = favoritesState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Error -> ErrorScreen(
                message = state.message,
                onRetry = { /* Room auto-retries */ },
            )

            is UiState.Success -> {
                if (state.data.isEmpty()) {
                    EmptyScreen(
                        message = "No favorites yet. Tap ♡ on any user to save them.",
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.data,
                            key = { it.id }
                        ) { user ->
                            UserListItem(
                                user = user,
                                onUserClick = onUserClick,
                                onFavoriteToggle = viewModel::onFavoriteToggle
                            )
                        }
                    }
                }
            }
        }
    }
}