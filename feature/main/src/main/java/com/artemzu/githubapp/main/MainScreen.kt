package com.artemzu.githubapp.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artemzu.githubapp.designsystem.GitHubAppTheme
import com.artemzu.githubapp.main.components.BottomBar
import com.artemzu.githubapp.main.navigation.MainNavigationGraph
import com.artemzu.githubapp.main.navigation.mainDestinations
import com.artemzu.githubapp.main.navigation.navigateToTopLevelDestination

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    navigateToAuthGraph: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect {
                when (it) {
                    MainViewModel.Event.Logout -> navigateToAuthGraph()
                }
            }
        }
    }

    MainScreenContent(navController = navController)
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomBar(
                destinations = mainDestinations,
                onNavigateToDestination = { navigateToTopLevelDestination(it, navController) },
                currentDestination = navController.currentBackStackEntryAsState().value?.destination
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MainNavigationGraph(navController = navController)
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    GitHubAppTheme {
        MainScreenContent(navController = rememberNavController())
    }
}