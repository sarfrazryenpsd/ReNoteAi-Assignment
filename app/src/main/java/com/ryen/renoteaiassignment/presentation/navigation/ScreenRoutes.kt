package com.ryen.renoteaiassignment.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object UserListRoute

@Serializable
data class UserDetailRoute(val userId: Int)

@Serializable
data object FavoritesRoute