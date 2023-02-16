package com.artemzu.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.artemzu.auth.ui.AuthScreen

const val authNavigationRoute = "auth_route"

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    this.navigate(authNavigationRoute, navOptions)
}

fun NavGraphBuilder.authGraph(
    navigateToMainGraph: () -> Unit
) {
    composable(route = authNavigationRoute) {
        AuthScreen(
            navigateToMainGraph = navigateToMainGraph
        )
    }
}