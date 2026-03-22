package com.ryen.renoteaiassignment.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ryen.renoteaiassignment.presentation.screen.favoriteScreen.FavoritesScreen
import com.ryen.renoteaiassignment.presentation.screen.userDetailScreen.UserDetailScreen
import com.ryen.renoteaiassignment.presentation.screen.userListScreen.UserListScreen


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = UserListRoute,
        enterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(300)
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(300)
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End,
                        tween(300)
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End,
                        tween(300)
                    )
        }
    ) {
        composable<UserListRoute> {
            UserListScreen(
                onUserClick = { userId ->
                    navController.navigate(UserDetailRoute(userId))
                },
                onFavoritesClick = {
                    navController.navigate(FavoritesRoute)
                }
            )
        }

        composable<UserDetailRoute> { backStackEntry ->
            val route: UserDetailRoute = backStackEntry.toRoute()
            UserDetailScreen(
                userId = route.userId,
                onBack = { navController.navigateUp() }
            )
        }

        composable<FavoritesRoute> {
            FavoritesScreen(
                onUserClick = { userId ->
                    navController.navigate(UserDetailRoute(userId))
                },
                onBack = { navController.navigateUp() }
            )
        }
    }
}