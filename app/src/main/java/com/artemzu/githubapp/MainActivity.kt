package com.artemzu.githubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.artemzu.githubapp.designsystem.GitHubAppTheme
import com.artemzu.githubapp.navigation.RootGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SystemViews()
                    RootGraph(
                        navHostController = rememberNavController()
                    )
                }
            }
        }
    }

    @Composable
    private fun SystemViews() {
        val systemUiController = rememberSystemUiController()
        val statusBarColor = MaterialTheme.colorScheme.primaryContainer
        val navigationBarColor = MaterialTheme.colorScheme.background

        DisposableEffect(systemUiController) {
            systemUiController.setNavigationBarColor(navigationBarColor)
            systemUiController.setStatusBarColor(
                darkIcons = true,
                color = statusBarColor
            )
            onDispose {}
        }
    }
}