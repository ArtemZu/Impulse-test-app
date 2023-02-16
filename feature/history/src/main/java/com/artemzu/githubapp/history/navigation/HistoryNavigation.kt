package com.artemzu.githubapp.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.artemzu.githubapp.history.HistoryScreen

const val historyNavigationRoute = "history_route"

fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.navigate(historyNavigationRoute, navOptions)
}

fun NavGraphBuilder.historyScreen() {
    composable(route = historyNavigationRoute) {
        HistoryScreen()
    }
}