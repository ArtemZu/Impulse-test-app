package com.artemzu.githubapp.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.artemzu.githubapp.history.navigation.historyScreen
import com.artemzu.githubapp.history.navigation.navigateToHistory
import com.artemzu.githubapp.main.R
import com.artemzu.githubapp.repositories.navigation.navigateToRepositories
import com.artemzu.githubapp.repositories.navigation.repositoriesNavigationRoute
import com.artemzu.githubapp.repositories.navigation.repositoriesScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: String = repositoriesNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        repositoriesScreen()
        historyScreen()
    }
}

/**
 * Map of top level destinations to be used in the BottomBar. The key is the route.
 */
val mainDestinations: List<MainDestination> = MainDestination.values().asList()

/**
 * UI logic for navigating to a top level destination in the app. Top level destinations have
 * only one copy of the destination of the back stack, and save and restore state whenever you
 * navigate to and from it.
 *
 * @param mainDestination: The destination the app needs to navigate to.
 */
fun navigateToTopLevelDestination(
    mainDestination: MainDestination,
    navController: NavHostController
) {
    val topLevelNavOptions = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    when (mainDestination) {
        MainDestination.REPOSITORIES ->
            navController.navigateToRepositories(topLevelNavOptions)

        MainDestination.HISTORY -> navController.navigateToHistory(topLevelNavOptions)
    }
}

enum class MainDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    REPOSITORIES(
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.repositories,
        titleTextId = R.string.repositories,
    ),
    HISTORY(
        selectedIcon = Icons.Default.Info,
        unselectedIcon = Icons.Outlined.Info,
        iconTextId = R.string.history,
        titleTextId = R.string.history,
    )
}