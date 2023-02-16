package com.artemzu.githubapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.artemzu.auth.navigation.authGraph
import com.artemzu.auth.navigation.authNavigationRoute
import com.artemzu.auth.navigation.navigateToAuth
import com.artemzu.githubapp.MainActivityViewModel
import com.artemzu.githubapp.main.navigation.mainGraph
import com.artemzu.githubapp.main.navigation.mainNavigationRoute
import com.artemzu.githubapp.main.navigation.navigateToMain

@Composable
fun RootGraph(
    mainActivityViewModel: MainActivityViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val isUserAuthorized by mainActivityViewModel.isUserAuthorized.collectAsStateWithLifecycle()

    isUserAuthorized?.let { isAuthorized ->
        val startDestination = if (isAuthorized) mainNavigationRoute else authNavigationRoute
        NavHost(
            route = ROOT_ROUTE,
            navController = navHostController,
            startDestination = startDestination
        ) {
            authGraph(
                navigateToMainGraph = {
                    navHostController.navigateToMain(
                        navOptions {
                            popUpTo(ROOT_ROUTE) {
                                inclusive = true
                            }
                        }
                    )
                }
            )

            mainGraph(
                navigateToAuthGraph = {
                    navHostController.navigateToAuth(
                        navOptions {
                            popUpTo(ROOT_ROUTE) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
    }
}

private const val ROOT_ROUTE = "root_route"