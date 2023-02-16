package com.artemzu.githubapp.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.artemzu.githubapp.main.MainScreen

const val mainNavigationRoute = "main_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(mainNavigationRoute, navOptions)
}

fun NavGraphBuilder.mainGraph(
    navigateToAuthGraph: () -> Unit
) {
    composable(route = mainNavigationRoute) {
        MainScreen(
            navigateToAuthGraph = navigateToAuthGraph
        )
    }
}