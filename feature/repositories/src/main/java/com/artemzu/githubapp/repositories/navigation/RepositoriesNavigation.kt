package com.artemzu.githubapp.repositories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.artemzu.githubapp.repositories.RepositoriesScreen

const val repositoriesNavigationRoute = "repositories_route"

fun NavController.navigateToRepositories(navOptions: NavOptions? = null) {
    this.navigate(repositoriesNavigationRoute, navOptions)
}

fun NavGraphBuilder.repositoriesScreen() {
    composable(route = repositoriesNavigationRoute) {
        RepositoriesScreen()
    }
}